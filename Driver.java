public class Driver {
    public static void main(String[] args) {
        // 1) Zero polynomial
        Polynomial p = new Polynomial();
        System.out.println("p(3) = " + p.evaluate(3)); // expected 0.0

        // 2) Build p1: old dense was [6, 0, 0, 5]  =>  6 + 5x^3
        double[] c1 = { 6, 5 };
        int[]    e1 = { 0, 3 };
        Polynomial p1 = new Polynomial(c1, e1);

        // 3) Build p2: old dense was [0, -2, 0, 0, -9]  =>  -2x - 9x^4
        double[] c2 = { -2, -9 };
        int[]    e2 = {  1,   4 };
        Polynomial p2 = new Polynomial(c2, e2);

        // 4) Add: s = p1 + p2
        Polynomial s = p1.add(p2);

        // 5) Evaluate like the original driver
        System.out.println("s(0.1) = " + s.evaluate(0.1));

        // 6) Root check like the original driver
        if (s.hasRoot(1)) {
            System.out.println("1 is a root of s");
        } else {
            System.out.println("1 is not a root of s");
        }

        // 7) Multiply to confirm part (c)
        Polynomial m = p1.multiply(p2);  // (6 + 5x^3) * (-2x - 9x^4)
        System.out.println("m(2) = " + m.evaluate(2));
    }
}
