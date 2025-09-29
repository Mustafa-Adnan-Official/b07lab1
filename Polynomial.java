public class Polynomial {
    private double [] coeffs_map_degree; // coeffs_map_degree[i] is the coef for x^i
    
    public Polynomial(){
        coeffs_map_degree = new double[1];
        coeffs_map_degree[0] = 0.0;
    }

    public Polynomial(double [] coeffs_map_degree) {
        //this.coeffs_map_degree = coeffs_map_degree; WRONG: aliasing not a copy
        // aliasing: two references point to same object in mem
        // copy: two references point to two diff objects in mem, with the same content
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
        double [] new_coeffs = new double[highest_deg]; // new arr with sufficient size for highest degree (init. to 0.0)
        
        // add coeffs for degrees that both polys have
        for (int i=0; i<lower_deg; i++){
            new_coeffs[i] = this.coeffs_map_degree[i] + p.coeffs_map_degree[i];
        }
        // copy remaining coeffs from the poly with the higher degree
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