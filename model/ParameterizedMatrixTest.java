/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package pl.polsl.marta.wower.model;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

/**
 * Parameterized tests for Matrix class.
 * @author Marta Wower
 * @version 1.0.0
 */
public class ParameterizedMatrixTest {

    /**
     * Parametrized test of getSize method.
     * @param hw a number of a range 11-55, where the first digit is number of rows and the second digit is number of columns
     * @throws WrongSizeException if number of rows or columns is not in the range [1, 5].
     */
    @ParameterizedTest
    @ValueSource(ints = {11, 22, 33, 44}) 
    public void pTestGetSize(int hw) throws WrongSizeException {
        System.out.println("getSize");
        int mHeight = hw/10;
        int mWidth = hw % 10; 
        Matrix instance = new Matrix(mHeight, mWidth);
        String expResult = mHeight + "x" + mWidth;
        String result = instance.getSize();
        assertEquals(expResult, result);
    }
    
    /**
     * Parameterized test of fillMatrix_int method
     * @param x a number that the matrix will be filled with
     * @throws WrongSizeException if number of rows or columns is not in the range [1, 5]. 
     */
    @ParameterizedTest
    @ValueSource(ints = {11, 22, 33, 44}) 
    public void pTestFillMatrixInt(int x) throws WrongSizeException {
        System.out.println("fillMatrix");
        int element = x;
        Matrix instance = new Matrix(3,4);
        instance.fillMatrix(element);
        ArrayList<Integer> expResult = new ArrayList<>();
        for(int i = 0; i < instance.getHeight()*instance.getWidth(); i++)
            expResult.add(element);
        
        ArrayList<Integer> result = new ArrayList<>();
        for(int i = 0; i < instance.getHeight(); i++)
        {
            for(int j = 0; j < instance.getWidth(); j++)
            {
                result.add(instance.getMatrixArray().get(i).get(j));
            }
        }
        
        assertEquals(expResult, result);
    }
}
