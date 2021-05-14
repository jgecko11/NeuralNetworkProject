import java.util.ArrayList;
import java.util.List;

/**
 * GrecoMatrix.java
 *
 * Authors: Jack Greco
 * Date: 2-24-21
 * -JMG
 */
public class GrecoMatrix {
    //INSTANCE-DATA
    private int rows;
    private int columns;
    private double[][] data;

    //CONSTRUCTORS
    //Simple Constructor
    public GrecoMatrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.data = new double[rows][columns];
        for (int r = 0; r < this.rows; r++) {
            for (int c = 0; c < this.columns; c++) {
                this.data[r][c] = Math.random() * 2 - 1; //Fill data matrix with random doubles between -1 and +1
            }
        }
    }

    //HELPER-METHODS (all self explanatory)
    public double get(int row, int column){
        return this.data[row][column];
    }
    public int getRows(){
        return this.rows;
    }
    public int getColumns(){
        return this.columns;
    }
    public void set(int row, int column, double data){
        this.data[row][column] = data;
    }
    public List<Double> toList(){
        List<Double> toReturn = new ArrayList<Double>();
        for(int r = 0; r < this.rows; r++){
            for(int c = 0; c < this.columns; c++){
                toReturn.add(this.data[r][c]);
            }
        }
        return toReturn;
    }
    public static GrecoMatrix fromArray(double[] array){
        GrecoMatrix toReturn = new GrecoMatrix(array.length,1);
        for(int i = 0; i < array.length; i++){
            toReturn.set(i,0,array[i]);
        }
        return toReturn;
    }

    //MAIN-METHODS
    //this version of the add method increases all values in the data matrix by the inputted scalar value.
    public void add(double scalar){
        for(int r = 0; r < this.rows; r++){
            for(int c = 0; c < this.columns; c++){
                this.data[r][c] += scalar;
            }
        }
    }
    //this version of the add method takes a matrix and adds each matching value to this matrix's data
    public void add(GrecoMatrix matrix){
        if(this.columns != matrix.getColumns() || this.rows != matrix.getRows()){
            System.out.println("ADDITION ERROR: SIZE INCOMPATIBILITY");
            return;
        }
        for(int r = 0; r < this.rows; r++){
            for(int c = 0; c < this.columns; c++){
                this.data[r][c] = this.data[r][c] + matrix.get(r,c);
            }
        }
    }
    //this returns an exact copy of the inputted HughesMatrix
    public static GrecoMatrix clone(GrecoMatrix matrix){
        GrecoMatrix toReturn = new GrecoMatrix(matrix.getRows(),matrix.getColumns());
        for(int r = 0; r < matrix.getRows(); r++){
            for(int c = 0; c < matrix.getColumns(); c++){
                toReturn.set(r,c,matrix.get(r,c));
            }
        }
        return toReturn;
    }
    //This returns a new matrix assembled by the element-based subtraction of a - b
    public static GrecoMatrix subtract(GrecoMatrix a, GrecoMatrix b){
        if(a.getColumns() != b.getColumns() || a.getRows() != b.getRows()){
            System.out.println("SUBTRACTION ERROR: SIZE INCOMPATIBILITY");
            return null;
        }
        GrecoMatrix toReturn = new GrecoMatrix(a.getRows(),a.getColumns());
        for(int r = 0; r < a.getRows(); r++){
            for(int c = 0; c < a.getColumns(); c++){
                toReturn.set(r,c,(a.get(r,c)-b.get(r,c)));
            }
        }
        return toReturn;
    }
    //This version of the multiply method multiplies matrix a by matrix b and returns the resulting HughesMatrix
    public static GrecoMatrix multiply(GrecoMatrix a, GrecoMatrix b){
        GrecoMatrix toReturn = new GrecoMatrix(a.getRows(), b.getColumns());
        for(int r = 0; r < a.getRows(); r++){
            for(int c = 0; c < b.getColumns(); c++){
                double curSum = 0;
                for(int z = 0; z < a.getColumns(); z++){
                    //Matrix multiplication sucks lol it's row times column equals one entry and I hate it a lot
                    curSum += a.get(r,z) * b.get(z,c);
                }
                toReturn.set(r,c,curSum);
            }
        }
        return toReturn;
    }
    //this version of the multiply method multiplies every value in this HughesMatrix by an inputted scalar
    public void multiply(double scalar){
        for(int r = 0; r < this.rows; r++){
            for(int c = 0; c < this.columns; c++){
                this.data[r][c] = this.data[r][c] * scalar;
            }
        }
    }
    //this version of the multiply method does ELEMENT-WISE multiplication of this HughesMatrix by the inputted HughesMatrix
    public void multiply(GrecoMatrix matrix){
        if(this.columns != matrix.getColumns() || this.rows != matrix.getRows()){
            System.out.println("ELEMENT MULTIPLICATION ERROR: SIZE INCOMPATIBILITY");
            return;
        }
        for(int r = 0; r < this.rows; r++){
            for(int c = 0; c < this.columns; c++){
                //Element multiplication
                this.data[r][c] = this.data[r][c] * matrix.get(r,c);
            }
        }
    }
    //this is the sigmoid function--it implements the sigmoid curve as an initialization step for learning gradients
    public void initSigmoid(){
        for(int r = 0; r < this.rows; r++){
            for(int c = 0; c < this.columns; c++){
                //The internet says this is the sigmoid curve...
                this.data[r][c] = 1/(1+Math.exp(-this.data[r][c]));
            }
        }
    }
    //this is the derivative of sigmoid function--it returns a HughesMatrix after applying the derivative of sigmoid
    //to this HughesMatrix
    public GrecoMatrix deriveSigmoid(){
        GrecoMatrix toReturn = new GrecoMatrix(this.rows,this.columns);
        for(int r = 0; r < this.rows; r++){
            for(int c = 0; c < this.columns; c++){
                double derivedSigmoid = this.data[r][c] * (1-this.data[r][c]);
                toReturn.set(r,c,derivedSigmoid);
            }
        }
        return toReturn;
    }
}
