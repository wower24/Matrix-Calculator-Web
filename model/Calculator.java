/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.marta.wower.model;

/**
 * This class holds the calculation logic of the program.
 * @author Marta Wower
 * @version 1.0.2
 */
public class Calculator 
{
    /**
     * The first Matrix of an equation.
     * The order matters for multiplication.
     */
    private Matrix a;
    /**
     * The second Matrix of an equation.
     * The order matters for multiplication.
     */
    private Matrix b;
    /**
     * Matrix holding a result of equation.
     */
    private Matrix result;
    /**
     * The chosen calculation.
     * @author Marta Wower
     */
    public enum Choice
    {
        /**
         * Chosen calculation: addition.
         */
        ADD,
        /**
         * Chosen calculation: subtraction.
         */
        SUBTRACT,
        /**
         * Chosen calculation: multiplication.
         */
        MULTIPLY,
        /**
         * Wrong choice (other than the 3 before).
         */
        OTHER
    }
    
    private Choice choice;
    
    /**
     * Default Calculator constructor.
     * It creates an empty result matrix.
     */
    public Calculator()
    {
        result = new Matrix();
    }
    
    /**
     * Calculator constructor with 3 parameters.
     * @param a The first matrix of equation.
     * @param b The second matrix of equation.
     * @param sChoice Information about which calculation is going to be done.
     * @throws pl.polsl.marta.wower.model.WrongSizeException if sizes of matrices are not right for wanted calculation.
     * @throws pl.polsl.marta.wower.model.UnavailableChoiceException if users choice of calculation is not correct. 
     */
    public Calculator (Matrix a, Matrix b, String sChoice) throws WrongSizeException, UnavailableChoiceException
    {
        boolean isLegit = true;
        
        if(sChoice.length() == 0)
            throw new UnavailableChoiceException("Wrong input.");
        else switch (sChoice) {
            case "addition":
                this.choice = Choice.ADD;
                break;
            case "subtraction":
                this.choice = Choice.SUBTRACT;
                break;
            case "multiplication":
                this.choice = Choice.MULTIPLY;
                break;
            default:
            {
                isLegit = false;
                throw new UnavailableChoiceException("Wrong input.");
            }
        }
        
        this.a = a;
        this.b = b;
        
        if(isLegit)
            calculate(this.choice);
    }
   /**
    * Matrix A getter.
    * @return the first matrix of equation.
    */ 
    public Matrix getMatrixA() {
        return this.a;
    }
    /**
    * Matrix B getter.
    * @return the second matrix of equation.
    */ 
    public Matrix getMatrixB() {
        return this.b;
    }
    /**
    * Result getter.
    * @return the result matrix of equation.
    */ 
    public Matrix getResult() {
        return this.result;
    }
    
    /**
     * Calls a proper calculation method.
     * @param x Calculation chosen by a user.
     * @throws pl.polsl.marta.wower.model.UnavailableChoiceException if users choice of calculation is not correct.
     * @throws pl.polsl.marta.wower.model.WrongSizeException if sizes of matrices are not right for wanted calculation.
     */
    private void calculate(Choice x) throws UnavailableChoiceException, WrongSizeException
    {                
           if(x == Choice.ADD)
               this.addition();
           else if(x == Choice.SUBTRACT)
               this.subtraction();
           else if(x == Choice.MULTIPLY)
               this.multiplication();
           else
               throw new UnavailableChoiceException("Wrong input.");
    }
    //addition - only same sized matrices
    /**
     * Adds matrix A to matrix B.
     * @return The result is a new matrix.
     * @throws WrongSizeException if matrix sizes are not the same.
     */
    private Matrix addition() throws WrongSizeException
    {
        if (this.a.getSize().equals(this.b.getSize()))
        {
            //operation possible, perform addition
            result = new Matrix(this.a.getHeight(), this.a.getWidth());
            
            for(int i = 0; i < this.a.getHeight(); i++)
            {
                for(int j = 0; j < this.a.getWidth(); j++)
                {
                    int element = this.a.getMatrixArray().get(i).get(j) + this.b.getMatrixArray().get(i).get(j);
                    this.result.getMatrixArray().get(i).add(j, element);
                }
                    
            }
        }
        else
        {
            throw new WrongSizeException("Addition is not possible. Sizes of matrices are not the same.");
        }
        return result;
    }
    //subtraction - only same sized matrices
    /**
     * Subtracts matrix B from matrix A.
     * @return The result is a new matrix.
     * @throws WrongSizeException if matrix sizes are not the same.
     */
    private Matrix subtraction() throws WrongSizeException
    {
        if (this.a.getSize().equals(this.b.getSize()))
        {
            //operation possible, perform subtraction
            result = new Matrix(this.a.getHeight(), this.a.getWidth());
            
            for(int i = 0; i < this.a.getHeight(); i++)
            {
                for(int j = 0; j < this.a.getWidth(); j++)
                {
                    int element = this.a.getMatrixArray().get(i).get(j) - this.b.getMatrixArray().get(i).get(j);
                    this.result.getMatrixArray().get(i).add(j, element);
                }
                    
            }
        }
        else
        {
            throw new WrongSizeException("Subtraction not possible. Sizes of matrices are not the same.");
        }
        return result;
    }
    //multiplication - only mxn nxp matrices
    /**
     * Calculates method A times method B. 
     * @return The result is a new matrix.
     * @throws WrongSizeException if matrix sizes are not correct.
     */
    private Matrix multiplication() throws WrongSizeException
    {
        if(this.a.getWidth() == this.b.getHeight())
        {
            //operation possible, perform multiplication
            //result of multiplication is a matrix mxp
            result = new Matrix(this.a.getHeight(), this.b.getWidth());
            result.fillMatrix(0);
            
            for(int i = 0; i < this.result.getHeight(); i++)
            {
                for(int j = 0; j < this.result.getWidth(); j++)
                {
                    for(int k = 0; k < this.b.getHeight(); k++)
                    {
                        int element = this.result.getMatrixArray().get(i).get(j)
                                + (this.a.getMatrixArray().get(i).get(k) * this.b.getMatrixArray().get(k).get(j));
                        this.result.getMatrixArray().get(i).set(j, element);
                    }
                }
            }
        }
        else
        {
            throw new WrongSizeException("Multiplication not possible. Sizes of matrices are not correct.");
        }      
        return result;
    }

}
