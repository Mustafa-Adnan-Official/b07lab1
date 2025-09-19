public class Polynomial {
    private double [] coeffs_map_degree; // coeffs_map_degree[i] is the coef for x^i
    
    public Polynomial(){
        coeffs_map_degree = new double[1];
        coeffs_map_degree[0] = 0.0;
    }

    public Polynomial(double [] coeffs_map_degree) {
        //this.coeffs_map_degree = coeffs_map_degree; WRONG: aliasing not a copy
        if (coeffs_map_degree == null || coeffs_map_degree.length == 0) {
            this.coeffs_map_degree = new double[1];
            this.coeffs_map_degree[0] = 0.0;
            // or in one line: this.coeffs_map_degree = new double[]{0.0};
        }
        else{
            this.coeffs_map_degree = new double[coeffs_map_degree.length];
            for (int i=0; i<coeffs_map_degree.length; i++)
                this.coeffs_map_degree[i] = coeffs_map_degree[i];
        }
    }

    public Polynomial add (Polynomial p) {
        int highest_deg = Math.max(this.coeffs_map_degree.length, p.coeffs_map_degree.length);
        int lower_deg = Math.min(this.coeffs_map_degree.length, p.coeffs_map_degree.length);
        double [] new_coeffs = new double[highest_deg];
        
        for (int i=0; i<lower_deg; i++){
            new_coeffs[i] = this.coeffs_map_degree[i] + p.coeffs_map_degree[i];
        }

        if (this.coeffs_map_degree.length > p.coeffs_map_degree.length) {
            for (int i=lower_deg; i<highest_deg; i++){
                new_coeffs[i] = this.coeffs_map_degree[i];
            }
        } 
        else {
            for (int i=lower_deg; i<highest_deg; i++){
                new_coeffs[i] = p.coeffs_map_degree[i];
            }
        }
        return new Polynomial(new_coeffs);
    }

    public double evaluate (double x) {
        double result = 0.0;
        int i = 0;
        while (i < this.coeffs_map_degree.length) {
            result += this.coeffs_map_degree[i] * Math.pow(x,i);
            i++;
        }
        return result;
    }

    public boolean hasRoot (double x) {
        return this.evaluate(x) == 0;
    }
}

/*
public static void main(String[] args) {
    Polynomial p1 = new Polynomial(new double[] {1,2,3}); // 1 + 2x + 3x^2
    Polynomial p2 = new Polynomial(new double[] {0,1,0,4}); // 0 + 1x + 0x^2 + 4x^3

    Polynomial p3 = p1.add(p2); // should be 1 + 3x + 3x^2 + 4x^3

    System.out.println("p1(2) = " + p1.evaluate(2)); // should be 17
    System.out.println("p2(2) = " + p2.evaluate(2)); // should be 34
    System.out.println("p3(2) = " + p3.evaluate(2)); // should be 51

    System.out.println("p1 has root at x= -0.5: " + p1.hasRoot(-0.5)); // should be false
    System.out.println("p2 has root at x= 0: " + p2.hasRoot(0)); // should be true
    System.out.println("p3 has root at x= -1: " + p3.hasRoot(-1)); // should be false
}
*/