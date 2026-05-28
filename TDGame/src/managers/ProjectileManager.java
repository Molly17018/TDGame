/*
 * Decompiled with CFR 0.151.
 */
package managers;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import enemies.Enemy;
import helpz.Constants;
import helpz.LoadSave;
import scenes.Playing;
import towers.Projectile;
import towers.Tower;

public class ProjectileManager {
	private Playing playing;
	private ArrayList<Projectile> projectiles = new ArrayList();
	private ArrayList<Explosion> explosions = new ArrayList();
	private BufferedImage[] projImgs;
	private int projId = 0;
	private BufferedImage[] explosionImgs;

	public ProjectileManager(Playing playing) {
		this.playing = playing;
		this.importImgs();
	}

	private void importImgs() {
		BufferedImage atlas = LoadSave.getSpriteAtlas();
		this.projImgs = new BufferedImage[5];
		int i = 0;
		while (i < 3) {
			this.projImgs[i] = atlas.getSubimage((7 + i) * 32, 32, 32, 32);
			++i;
		}
		projImgs[3] = atlas.getSubimage(5 * 32, 3 * 32, 32, 32);
		projImgs[4] = atlas.getSubimage(6 * 32, 3 * 32, 32, 32);
		this.imporeExplosionImgs(atlas);
	}

	private void imporeExplosionImgs(BufferedImage atlas) {
		this.explosionImgs = new BufferedImage[7];
		int i = 0;
		while (i < 7) {
			this.explosionImgs[i] = atlas.getSubimage(i * 32, 64, 32, 32);
			++i;
		}
	}

	public void newProjectile(Tower t, Enemy e) {
		int type = this.getProjType(t);
		int xDist = (int) (t.getX() - e.getX());
		int yDist = (int) (t.getY() - e.getY());
		int totalDist = Math.abs(xDist) + Math.abs(yDist);
		float xPer = (float) Math.abs(xDist) / (float) totalDist;
		float xSpeed = xPer * Constants.Projectiles.GetSpeed(type);
		float ySpeed = Constants.Projectiles.GetSpeed(type) - xSpeed;
		if (t.getX() > e.getX()) {
			xSpeed *= -1.0f;
		}
		if (t.getY() > e.getY()) {
			ySpeed *= -1.0f;
		}
		float rotate = 0.0f;
		if (type == 0) {
			float arcValue = (float) Math.atan((float) yDist / (float) xDist);
			rotate = (float) Math.toDegrees(arcValue);
			if (xDist < 0) {
				rotate += 180.0f;
			}
		}
		for (Projectile p : this.projectiles) {
			if (p.isActive() || p.getProjectileType() != type) {
				continue;
			}
			p.reuse(t.getX() + 16, t.getY() + 16, xSpeed, ySpeed, rotate, t.getDmg());
			return;
		}
		this.projectiles.add(
				new Projectile(t.getX() + 16, t.getY() + 16, xSpeed, ySpeed, rotate, this.projId++, type, t.getDmg()));
	}

	/**
	 * Creates projectiles for the Tree towers.
	 * @param t The tower which is shooting a projectile
	 */
	public void newProjectile(Tower t) {
		int type = this.getProjType(t);
		int xSpeed = 0;
		float ySpeed = 0.1f;
		float rotate = 0.1f;
		float time = 150f;
		for (Projectile p : this.projectiles) {
			if (p.isActive() || p.getProjectileType() != type) {
				continue;
			}
			p.reuse(t.getX() + 16, t.getY() + 16, xSpeed, ySpeed, rotate, time);
			return;
		}
		this.projectiles.add(
				new Projectile(t.getX() + 16, t.getY() + 16, xSpeed, ySpeed, rotate, this.projId++, type, time));
	}

	public void update() {
		for (Projectile p : this.projectiles) {
			if (!p.isActive()) {
				continue;
			}
			p.move();
			if (this.isProjHittingEnemy(p) && p.getProjectileType() != 3) {
				p.setActive(false);
				if (p.getProjectileType() != 2) {
					continue;
				}
				this.explosions.add(new Explosion(p.getPos()));
				this.explodeOnEnemies(p);
				continue;
			}
			if (p.getProjectileType() == 3 || p.getProjectileType() == 4) { //Coin / Hearts
				p.countdownTime();
				if (p.getTime() <= 0) {
					p.setActive(false);
				}
			}
			if (!this.isProjectilOutOfBounds(p)) {
				continue;
			}
			p.setActive(false);
		}
		for (Explosion e : this.explosions) {
			if (e.getExlpoIndex() >= 7) {
				continue;
			}
			e.update();
		}
	}

	private void explodeOnEnemies(Projectile p) {
		for (Enemy e : this.playing.getEnemyManager().getEnemies()) {
			float yDist;
			if (!e.isAlive()) {
				continue;
			}
			float radius = 40.0f;
			float xDist = Math.abs(p.getPos().x - e.getX());
			float realDist = (float) Math.hypot(xDist, yDist = Math.abs(p.getPos().y - e.getY()));
			if (!(realDist <= radius)) {
				continue;
			}
			e.hurt(p.getDamage());
		}
	}

	private boolean isProjHittingEnemy(Projectile p) {
		for (Enemy e : this.playing.getEnemyManager().getEnemies()) {
			if (!e.isAlive() || !e.getBounds().contains(p.getPos())) {
				continue;
			}
			e.hurt(p.getDamage());
			if (p.getProjectileType() == 1 && e.getEnemyType() != 1) {
				e.slow();
			}
			return true;
		}
		return false;
	}

	/**
	 * Checks if a projectile is out of bounds
	 * @param p Projectile
	 * @return Boolean
	 */
	private boolean isProjectilOutOfBounds(Projectile p) {
		return !(p.getPos().x >= 0.0f) || !(p.getPos().x <= 640.0f) || !(p.getPos().y >= 0.0f)
				|| !(p.getPos().y <= 640.0f);
	}

	public void draw(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		for (Projectile p : this.projectiles) {
			if (!p.isActive()) {
				continue;
			}
			if (p.getProjectileType() == 0) {
				g2d.translate(p.getPos().x, p.getPos().y);
				g2d.rotate(Math.toRadians(p.getRotation()));
				g2d.drawImage(this.projImgs[p.getProjectileType()], -16, -16, null);
				g2d.rotate(Math.toRadians(-p.getRotation()));
				g2d.translate(-p.getPos().x, -p.getPos().y);
				continue;
			}
			g2d.drawImage(this.projImgs[p.getProjectileType()], (int) p.getPos().x - 16, (int) p.getPos().y - 16, null);
		}
		this.drawExplosions(g2d);
	}

	private void drawExplosions(Graphics2D g2d) {
		for (Explosion e : this.explosions) {
			if (e.getExlpoIndex() >= 7) {
				continue;
			}
			g2d.drawImage(this.explosionImgs[e.getExlpoIndex()], (int) e.getPos().x - 16, (int) e.getPos().y - 16,
					null);
		}
	}

	private int getProjType(Tower t) {
		switch (t.getTowerType()) {
		case 0: {
			return 2;
		}
		case 1: {
			return 0;
		}
		case 2: {
			return 1;
		}
		case 3: {
			return 3;
		}
		case 4: {
			return 4;
		}
		}
		return 0;
	}

	public void reset() {
		this.projectiles.clear();
		this.explosions.clear();
		this.projId = 0;
	}

	public class Explosion {
		private Point2D.Float pos;
		private int exploTick = 0;
		private int exploIndex = 0;

		public Explosion(Point2D.Float pos) {
			this.pos = pos;
		}

		public void update() {
			++this.exploTick;
			if (this.exploTick >= 12) {
				this.exploTick = 0;
				++this.exploIndex;
			}
		}

		public int getExlpoIndex() {
			return this.exploIndex;
		}

		public Point2D.Float getPos() {
			return this.pos;
		}
	}

}
