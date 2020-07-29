package InternalFiles;

public class Vector3 {
    public double x, y;

    public Vector3() {
        x = 0.0;
        y = 0.0;
    }

    public Vector3(double x, double y){
        this.x = x;
        this.y = y;
    }

    public Vector3(Vector3 v){
        this.x = v.x;
        this.y = v.y;
    }

    //Sets the vector to equal the cartesian equivalent of the polar vector composed of a length and an angle
    public void setFromPolar(double r, double theta) {
        this.x = Math.cos(theta) * r;
        this.y = Math.sin(theta) * r;
    }

    //Adds the components of a given Vector3 to this Vector3
    public void addto(Vector3 vector) {
        x += vector.x;
        y += vector.y;
    }

    //subtracts the components of a given vector from this vector and returns it as a new Vector3
    public Vector3 subtract(Vector3 vector) {
        vector.scalarMultiply(-1);
        return add(vector);
    }

    //subtracts the components of a given vector from this vector
    public void subtractfrom(Vector3 vector) {
        vector.scalarMultiply(-1);
        addto(vector);
    }

    //multiplies x and y by the given value
    public Vector3 scalarMultiply(double a) {
        x *= a;
        y *= a;
        return this;
    }

    //Length of vector
    public double magnitude() { return Math.hypot(x, y); }

    //Dot Product
    public double dot(Vector3 vector) {
        return vector.x * this.x + vector.y * this.y;
    }

    //Cross Product with z parameter equal to zero
    public Vector3 cross(Vector3 vector) {
        Vector3 a = new Vector3();
        Vector3 b = new Vector3();

        a.x = this.x;
        a.y = this.y;

        b.x = vector.x;
        b.y = vector.y;

        Vector3 result = a.cross(b);
        return result;
    }

    //Angular component of the vector if it was converted to polar coordinates
    //Returns angle in radians
    public double angle() {
        return Math.atan2(y, x);
    }

    //Returns angle between two vectors in radians.
    public double angleBetween(Vector3 vector) {
        return Math.acos(this.dot(vector) / (this.magnitude() * vector.magnitude()));
    }

    //Sets x and y rotated by the given angle in radians
    public void rotate(double angle){
        double tempX = x, tempY = y;
        x = Math.cos(angle) * tempX - Math.sin(angle) * tempY;
        y = Math.sin(angle) * tempX + Math.cos(angle) * tempY;
    }

    public Vector3 rounded(){
        return new Vector3(Math.round(x), Math.round(y));
    }

    public void round(){
        x = Math.round(x);
        y = Math.round(y);
    }

    public Vector3 rotated(double angle) {
        double newX = x * Math.cos(angle) - y * Math.sin(angle);
        double newY = x * Math.sin(angle) + y * Math.cos(angle);
        return new Vector3(newX, newY);
    }

    public double distanceToVector(Vector3 o){
        Vector3 offset = this.subtract(o);
        return offset.magnitude();
    }

    public Vector3 normalize(Vector3 other){
        double x = (this.x + other.x) / 2.0;
        double y = (this.y + other.y) / 2.0;

        return new Vector3(x, y);
    }

    public double norm() {
        return Math.hypot(x, y);
    }

    //Returns a new Vector3 that is the sum of both vectors.
    public Vector3 add(Vector3 v){
        return new Vector3(this.x + v.x, this.y + v.y);
    }

    public Vector3 clone(){
        return new Vector3(x, y);
    }

    public String toString(){
        return "(" + x + ", " + y + ")";
    }
}