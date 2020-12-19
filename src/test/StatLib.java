package test;

public class StatLib {
    // simple average

    public static float avg(float[] x) {
        if (x.length == 0)
            return 0;

        float sum = 0;
        for (float i : x) {
            sum += i;
        }
        return sum / x.length;
    }

    // returns the variance of X and Y
    public static float var(float[] x) {
        float var = 0;
        if (x.length > 0) {
            float u = avg(x);

            for (float v : x) {
                var += ((v - u) * (v - u));
            }

            return var * ((float) 1 / x.length);

        } else {
            return var;
        }
    }

    // returns the covariance of X and Y
    public static float cov(float[] x, float[] y) {
        float sum = 0;
        if (x.length == 0)
            return 0;

        for (int i = 0; i < x.length; i++) {
            sum += (x[i] - avg(x)) * (y[i] - avg(y));
        }
        return sum / (float) x.length;

    }

    // returns the Pearson correlation coefficient of X and Y
    public static float pearson(float[] x, float[] y) {
        return (float) (cov(x, y) / (float) (Math.sqrt(var(x)) * Math.sqrt(var(y))));
    }

    // performs a linear regression and returns the line equation
    public static Line linear_reg(Point[] points) {
        float[] x = new float[points.length];
        float[] y = new float[points.length];
        int counter = 0;
        for (Point p : points) {
            x[counter] = p.x;
            y[counter] = p.y;
            counter++;

        }
        float a = cov(x, y) / var(x);
        float b = avg(y) - a * avg(x);
        return new Line(a, b);
    }

    // returns the deviation between point p and the line equation of the
    public static float dev(Point p, Point[] points) {
        Line line = linear_reg(points);
        return dev(p, line);
    }


    // returns the deviation between point p and the line
    public static float dev(Point p, Line l) {
        return Math.abs(l.f(p.x) - p.y);
    }


}
