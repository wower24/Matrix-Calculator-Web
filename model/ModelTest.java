/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package pl.polsl.marta.wower.model;

import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for Model
 * @author Marta Wower
 * @version 1.0.2
 */
public class ModelTest {
    
    /**
     *
     */
    public ModelTest() {
    }
    
    /**
     *
     */
    @BeforeAll
    public static void setUpClass() {
    }
    
    /**
     *
     */
    @AfterAll
    public static void tearDownClass() {
    }
    
    /**
     *
     */
    @BeforeEach
    public void setUp() {
    }
    
    /**
     *
     */
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of createMatrix method, of class Model, in case of providing wrong size.
     * WrongSizeException should be thrown.
     * @throws pl.polsl.marta.wower.model.WrongSizeException if number of rows or columns is not in the range [1, 5].
     */
    @Test
    public void testCreateMatrixWrongSize() throws WrongSizeException {
        System.out.println("createMatrix_wrongSize");
        int mHeight = 0;
        int mWidth = 0;
        Model instance = new Model();
         WrongSizeException thrown = Assertions.assertThrows(WrongSizeException.class, () -> {
           instance.createMatrix(mHeight, mWidth);
         });
        Assertions.assertEquals("Height and Width of matrix must be integers between 1 and 5", thrown.getMessage());
    }
    
    /**
     * Test of createMatrix method, of class Model, matrix size 5x5.
     * No exception should be thrown.
     * @throws pl.polsl.marta.wower.model.WrongSizeException if number of rows or columns is not in the range [1, 5].
     */
    @Test
    public void testCreateMatrixFive() throws WrongSizeException {
        System.out.println("createMatrix_five");
        int mHeight = 5;
        int mWidth = 5;
        Model instance = new Model();
        instance.createMatrix(mHeight, mWidth);
        String expResult = "5x5";
        String result = instance.getMatrices().get(0).getSize();
        
        assertEquals(expResult, result);
    }
    
    /**
     * Test of createMatrix method, of class Model, matrix size 1x1.
     * No exception should be thrown.
     * @throws pl.polsl.marta.wower.model.WrongSizeException if number of rows or columns is not in the range [1, 5].
     */
    @Test
    public void testCreateMatrixOne() throws WrongSizeException {
        System.out.println("createMatrix_one");
        int mHeight = 1;
        int mWidth = 1;
        Model instance = new Model();
        instance.createMatrix(mHeight, mWidth);
        String expResult = "1x1";
        String result = instance.getMatrices().get(0).getSize();
        
        assertEquals(expResult, result);
    }

    /**
     * Test of fillMatrices method, of class Model.
     * @throws pl.polsl.marta.wower.model.WrongSizeException if number of rows or columns is not in the range [1, 5].
     */
    @Test
    public void testFillMatrices() throws WrongSizeException {
        System.out.println("fillMatrices");
        ArrayList<Matrix> mAL = new ArrayList<>();
        Matrix matrixA = new Matrix(2, 3);
        mAL.add(matrixA);
        
        ArrayList<Integer> elementsA = new ArrayList<>();
        for(int i = 0; i < matrixA.getHeight()*matrixA.getWidth(); i++)
            elementsA.add(1);
        
        Matrix matrixB = new Matrix(3, 2);
        mAL.add(matrixB);
        
        ArrayList<Integer> elementsB = new ArrayList<>();
        for(int i = 0; i < matrixB.getHeight()*matrixB.getWidth(); i++)
            elementsB.add(i);
        
        ArrayList<ArrayList<Integer>> iAL = new ArrayList<>();
        iAL.add(elementsA);
        iAL.add(elementsB);
        
        matrixA.fillMatrix(elementsA);
        matrixB.fillMatrix(elementsA);
        
        Model instance = new Model();
        instance.fillMatrices(mAL, iAL);
        
        ArrayList<Matrix> expResult = new ArrayList<>();
        expResult.add(matrixA);
        expResult.add(matrixB);
        
        ArrayList<Matrix> result = instance.getFilledMatrices();
        assertEquals(expResult, result);
    }

    /**
     * Test of chooseCalculation method, of class Model, wrong input provided.
     * @throws pl.polsl.marta.wower.model.UnavailableChoiceException if users choice of calculation is not correct. 
     * @throws pl.polsl.marta.wower.model.WrongSizeException if sizes of matrices are not right for wanted calculation.
     */
    @Test
    public void testChooseCalculationWrongInput() throws UnavailableChoiceException, WrongSizeException {
        System.out.println("chooseCalculation_wrongInput");
        String text = "";
        Model instance = new Model();
        
        instance.getMatrices().add(new Matrix(2, 3));
        instance.getMatrices().add(new Matrix(2, 3));
        
        UnavailableChoiceException thrown = Assertions.assertThrows(UnavailableChoiceException.class, () -> {
           instance.chooseCalculation(text);
         });
        Assertions.assertEquals("Wrong input.", thrown.getMessage());
    }
    
    /**
     * Test of chooseCalculation method, of class Model, right input on addition
     * @throws pl.polsl.marta.wower.model.UnavailableChoiceException if users choice of calculation is not correct. 
     * @throws pl.polsl.marta.wower.model.WrongSizeException if sizes of matrices are not right for wanted calculation.
     */
    @Test
    public void testChooseCalculationRightInputAdd() throws UnavailableChoiceException, WrongSizeException {
        System.out.println("chooseCalculation_add");
        String text = "addition";
        Model instance = new Model();
        
        Matrix matrixA = new Matrix(2, 3);
        Matrix matrixB = new Matrix(2, 3);
        matrixA.fillMatrix(1);
        matrixB.fillMatrix(2);
        instance.getMatrices().add(matrixA);
        instance.getMatrices().add(matrixB);
        
        instance.setCalculator(new Calculator(matrixA, matrixB, text));
        
        Matrix expResult = new Matrix(2, 3);
        expResult.fillMatrix(3);
        
        Matrix result = instance.getCalculator().getResult();
        
        assertEquals(expResult.getMatrixArray(), result.getMatrixArray());
    }
    
    /**
     * Test of chooseCalculation method, of class Model, right input on subtraction
     * @throws pl.polsl.marta.wower.model.UnavailableChoiceException if users choice of calculation is not correct. 
     * @throws pl.polsl.marta.wower.model.WrongSizeException if sizes of matrices are not right for wanted calculation.
     */
    @Test
    public void testChooseCalculationRightInputSubtract() throws UnavailableChoiceException, WrongSizeException {
        System.out.println("chooseCalculation_subtract");
        String text = "subtraction";
        Model instance = new Model();
        
        Matrix matrixA = new Matrix(2, 3);
        Matrix matrixB = new Matrix(2, 3);
        matrixA.fillMatrix(1);
        matrixB.fillMatrix(2);
        instance.getMatrices().add(matrixA);
        instance.getMatrices().add(matrixB);
        
        instance.setCalculator(new Calculator(matrixA, matrixB, text));
        
        Matrix expResult = new Matrix(2, 3);
        expResult.fillMatrix(-1);
        
        Matrix result = instance.getCalculator().getResult();
        
        assertEquals(expResult.getMatrixArray(), result.getMatrixArray());
    }
    
}
