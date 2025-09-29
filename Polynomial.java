import java.io.*; // for File, FileReader, BufferedReader

public class Polynomial {
    private double [] coeffs; // term i is coeffs[i] * x^(degrees[i])
    private int [] degrees; // matching exponents for coeffs[i]
    
    public Polynomial(){
        // zero polynomial
        this.coeffs = new double[0];
        this.degrees= new int[0];
    }

    public Polynomial(double [] coeffs, int [] degrees){
        this.coeffs = new double[coeffs.length];
        this.degrees = new int[degrees.length];

        for (int i=0; i<coeffs.length; i++){
            this.coeffs[i] = coeffs[i];
            this.degrees[i] = degrees[i];
        }
    }

    public Polynomial add (Polynomial p) {
        double[] temp_coeffs = new double[this.coeffs.length + p.coeffs.length];
        int[] temp_degrees = new int[this.degrees.length + p.degrees.length];
        int index = 0;

        for (int i = 0; i < this.coeffs.length; i++) {
            temp_coeffs[index] = this.coeffs[i];
            temp_degrees[index] = this.degrees[i];
            index++;
        }

        for (int j = 0; j < p.coeffs.length; j++) {
            boolean found = false;
            for (int k = 0; k < index; k++) {
                if (p.degrees[j] == temp_degrees[k]) {
                    temp_coeffs[k] += p.coeffs[j];
                    found = true;
                    break;
                }
            }
            if (!found) {
                temp_coeffs[index] = p.coeffs[j];
                temp_degrees[index] = p.degrees[j];
                index++;
            }
        }

        double[] result_coeffs = new double[index];
        int[] result_degrees = new int[index];
        for (int i = 0; i < index; i++) {
            result_coeffs[i] = temp_coeffs[i];
            result_degrees[i] = temp_degrees[i];
        }

        return new Polynomial(result_coeffs, result_degrees);
    }

    public double evaluate (double x) {
        double result = 0.0;
        for (int i = 0; i < this.coeffs.length; i++) {
            result += this.coeffs[i] * Math.pow(x, this.degrees[i]);
        }
        return result;
    }

    public boolean hasRoot (double x) {
        return this.evaluate(x) == 0;
    }

    public Polynomial multiply (Polynomial p) {
        double[] temp_coeffs = new double[this.coeffs.length * p.coeffs.length];
        int[] temp_degrees = new int[this.degrees.length * p.degrees.length];
        int index = 0;

        for (int i = 0; i < this.coeffs.length; i++) {
            for (int j = 0; j < p.coeffs.length; j++) {
                double new_coeff = this.coeffs[i] * p.coeffs[j];
                int new_degree = this.degrees[i] + p.degrees[j];
                boolean found = false;
                
                for (int k = 0; k < index; k++) {
                    if (temp_degrees[k] == new_degree) {
                        temp_coeffs[k] += new_coeff;
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    temp_coeffs[index] = new_coeff;
                    temp_degrees[index] = new_degree;
                    index++;
                }
            }
        }

        double[] result_coeffs = new double[index];
        int[] result_degrees = new int[index];
        for (int i = 0; i < index; i++) {
            result_coeffs[i] = temp_coeffs[i];
            result_degrees[i] = temp_degrees[i];
        }

        return new Polynomial(result_coeffs, result_degrees);
    }

    public Polynomial (File file) throws IOException {
        // open file
        BufferedReader br = new BufferedReader(new FileReader(file));

        // read the polynomial string
        String line = br.readLine();

        // close file
        br.close();

        // make sure first character is + or -
        if (line.charAt(0) != '+' && line.charAt(0) != '-') {
            line = "+" + line;
        }

        // split into pieces like ["+5", "-3x2", "+7x8"]
        String[] parts = line.split("(?=[+-])");

        double[] tempCoeffs = new double[parts.length];
        int[] tempDegrees = new int[parts.length];
        int index = 0;

        // go through each piece
        for (String term : parts) {
            if (term.equals("")) continue;

            double coeff;
            int degree;

            if (term.contains("x")) {
                String[] split = term.split("x");

                // coefficient (before x)
                String coeffStr = split[0];
                if (coeffStr.equals("+") || coeffStr.equals("")) coeff = 1.0;
                else if (coeffStr.equals("-")) coeff = -1.0;
                else coeff = Double.parseDouble(coeffStr);

                // exponent (after x)
                if (split.length == 1 || split[1].equals("")) {
                    degree = 1;
                } else {
                    degree = Integer.parseInt(split[1]);
                }
            } else {
                // constant term
                coeff = Double.parseDouble(term);
                degree = 0;
            }

            tempCoeffs[index] = coeff;
            tempDegrees[index] = degree;
            index++;
        }

        // copy into actual arrays
        this.coeffs = new double[index];
        this.degrees = new int[index];
        for (int i = 0; i < index; i++) {
            this.coeffs[i] = tempCoeffs[i];
            this.degrees[i] = tempDegrees[i];
        }

    }

    public void saveToFile(String filename) throws IOException {
        PrintStream out = new PrintStream(filename);

        // special case: zero polynomial
        if (coeffs.length == 0) {
            out.println("0");
            out.close();
            return;
        }

        String result = "";

        for (int i = 0; i < coeffs.length; i++) {
            double c = coeffs[i];
            int e = degrees[i];

            // sign
            if (c < 0) {
                result += "-";
            } else if (!result.equals("")) {
                result += "+";
            }

            double abs = Math.abs(c);

            if (e == 0) {
                // constant
                result += abs;
            } else {
                // x-term
                if (abs != 1.0) {
                    result += abs;
                }
                result += "x";
                if (e != 1) {
                    result += e;
                }
            }
        }

        out.println(result);
        out.close();
    }

}