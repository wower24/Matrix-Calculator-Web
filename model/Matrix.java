/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.marta.wower.model;

import java.util.ArrayList;

/**
 * This is a class for matrices.
 * @author Marta Wower
 * @version 1.0.2
 */
public class Matrix 
{
    //maximum size 5x5, independent dimensions
    /**
     * An array of elements that are stored in arrays of Integers.
     */
    private ArrayList<ArrayList<Integer>> matrixArray;
    /**
     * Size of matrix.
     * Height and width as Integers.
     * Whole size as String of a form "[rows]x[columns]".
     */
    private int height, width;
    /**
     * Size of matrix in form [height]x[width]
     */
    private String size = null;
    
    /**
     * Default constructor of Matrix.
     * It creates a matrix of size 0 (so technically, no matrix).
     */
    public Matrix()
    {
        //default - size 0 (no matrix)
        this.size = "0";
    }
    
    //height x width
    /**
     * Matrix constructor with 2 parameters.
     * @param height Number of rows of the matrix.
     * @param width Number of columns of the matrix.
     * @throws pl.polsl.marta.wower.model.WrongSizeException if number of rows or columns is not in the range [1, 5].
     */
    public Matrix (int height, int width) throws WrongSizeException
    {
        if(height < 1 || height > 5 || width < 1 || width > 5)
        {
            throw new WrongSizeException("Height and Width of matrix must be integers between 1 and 5");
        } 
        else
        {
            this.height = height;
            this.width = width;
            this.matrixArray = new ArrayList<>(height);
            for(int i = 0; i < height; i++)
                this.matrixArray.add(new ArrayList<>(width));
        //compare size strings to check if calculation is possible
            this.size = height + "x" + width;
        }
    }
    
    /**
     * Matrix size getter.
     * It returns the size in a following form:
     * [height]x[width]
     * @return The method returns size of a matrix. 
     */
    public String getSize()
    {
        return this.size;
    }
    /**
     * Matrix width getter.
     * @return number of columns of a matrix. 
     */
    public int getWidth()
    {
        return this.width;
    }
    /**
     * Matrix height getter.
     * @return number of rows of a matrix. 
     */
    public int getHeight()
    {
        return this.height;
    }
    
    /**
     * MatrixArray getter.
     * @return elements in form of two-dimensional ArrayList
     */
    public ArrayList<ArrayList<Integer>> getMatrixArray()
    {
        return this.matrixArray;
    }
    
    /**
     * Fills a matrix with elements passed in an ArrayList.
     * @param x An ArrayList of Integers that contains all elements of the matrix.
     */
    public void fillMatrix(ArrayList<Integer> x)
    {
        for(int i = 0; i < this.height; i++)
        {
            for(int j = 0; j < this.width; j++)
                matrixArray.get(i).add(j, x.get(j+i*this.width));
        }
    }
    
    /**
     * Sets all elements of a matrix to a given number.
     * @param element A number the whole matrix will be filled with.
     */
    public void fillMatrix(int element)
    {
        for(int i = 0; i < this.height; i++)
        {
            for(int j = 0; j < this.width; j++)
                matrixArray.get(i).add(j, element);
        }
    }
    
    /**
     * Turns matrix to string.
     * Separates elements with a space,
     * each line is a different row.
     * @return matrix in form of text
     */
    @Override
    public String toString() {
        String matrixText = "";
       
        for(int i = 0; i < this.height; i++)
        {
            for(int j = 0; j < this.width; j++)
                matrixText += matrixArray.get(i).get(j) + " ";
        
            matrixText += "\n";
               
        }
            return matrixText;
    }
            
}

