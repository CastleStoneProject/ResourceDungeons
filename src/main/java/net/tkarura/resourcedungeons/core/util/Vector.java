package net.tkarura.resourcedungeons.core.util;

public class Vector {

    public final static Vector MAX = new Vector(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
    public final static Vector MIN = new Vector(Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
    public final static Vector ZERO = new Vector(0.0, 0.0, 0.0);

    protected double x;
    protected double y;
    protected double z;

    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector() {
        this(0.0, 0.0, 0.0);
    }

    public Vector(int x, int y, int z) {
        this((double) x, (double) y, (double) z);
    }

    public Vector(float x, float y, float z) {
        this((double) x, (double) y, (double) z);
    }

    public Vector(Vector vector) {
        this(vector.x, vector.y, vector.z);
    }

    public Vector setX(double x) {
        this.x = x;
        return this;
    }

    public Vector setY(double y) {
        this.y = y;
        return this;
    }

    public Vector setZ(double z) {
        this.z = z;
        return this;
    }

    public Vector set(double x, double y, double z) {
        this.setX(x);
        this.setY(y);
        this.setZ(z);
        return this;
    }

    public Vector set(Vector vector) {
        this.set(vector.x, vector.y, vector.z);
        return this;
    }

    public Vector addX(double x) {
        this.x += x;
        return this;
    }

    public Vector addY(double y) {
        this.y += y;
        return this;
    }

    public Vector addZ(double z) {
        this.z += z;
        return this;
    }

    public Vector add(double x, double y, double z) {
        this.addX(x);
        this.addY(y);
        this.addZ(z);
        return this;
    }

    public Vector add(Vector vector) {
        this.add(vector.x, vector.y, vector.z);
        return this;
    }

    public Vector subX(double x) {
        this.x -= x;
        return this;
    }

    public Vector subY(double y) {
        this.y -= y;
        return this;
    }

    public Vector subZ(double z) {
        this.z -= z;
        return this;
    }

    public Vector sub(double x, double y, double z) {
        this.subX(x);
        this.subY(y);
        this.subZ(z);
        return this;
    }

    public Vector sub(Vector vector) {
        this.sub(vector.x, vector.y, vector.z);
        return this;
    }

    public Vector mulX(double x) {
        this.x *= x;
        return this;
    }

    public Vector mulY(double y) {
        this.y *= y;
        return this;
    }

    public Vector mulZ(double z) {
        this.z *= z;
        return this;
    }

    public Vector mul(double x, double y, double z) {
        this.mulX(x);
        this.mulY(y);
        this.mulZ(z);
        return this;
    }

    public Vector mul(Vector vector) {
        this.mul(vector.x, vector.y, vector.z);
        return this;
    }

    public Vector divX(double x) {
        this.x /= x;
        return this;
    }

    public Vector divY(double y) {
        this.y /= y;
        return this;
    }

    public Vector divZ(double z) {
        this.z /= z;
        return this;
    }

    public Vector div(double x, double y, double z) {
        this.divX(x);
        this.divY(y);
        this.divZ(z);
        return this;
    }

    public Vector div(Vector vector) {
        this.div(vector.x, vector.y, vector.z);
        return this;
    }

    public Vector zero() {
        this.set(0.0, 0.0, 0.0);
        return this;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public int getBlockX() {
        return (int) Math.round(this.x);
    }

    public int getBlockY() {
        return (int) Math.round(this.y);
    }

    public int getBlockZ() {
        return (int) Math.round(this.z);
    }

    public double length() {
        return Math.sqrt(
                this.x * this.x +
                        this.y * this.y +
                        this.z * this.z);
    }

    public double lengthSquare() {
        return
                x * x +
                        y * y +
                        z * z;
    }

    public static Vector copy(Vector from) {
        Vector to = new Vector();
        to.set(from);
        return to;
    }

    public static Vector max(Vector v1, Vector v2) {

        Vector result = new Vector();

        result.x = Math.max(v1.x, v2.x);
        result.y = Math.max(v1.y, v2.y);
        result.z = Math.max(v1.z, v2.z);

        return result;
    }

    public static Vector min(Vector v1, Vector v2) {

        Vector result = new Vector();

        result.x = Math.min(v1.x, v2.x);
        result.y = Math.min(v1.y, v2.y);
        result.z = Math.min(v1.z, v2.z);

        return result;
    }

    public String toString() {
        return "(" + this.x + ", " + this.y + ", " + this.z + ")";
    }

}
