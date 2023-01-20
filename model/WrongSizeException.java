/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pl.polsl.marta.wower.model;

/**
 * Exception thrown when sizes of matrices are not correct.
 * For example to add and subtract they have to be of the same size a x b,
 * to multiplicate their sizes should be a x b, b x c.
 * @author Marta Wower
 * @version 1.0.1
 */
public class WrongSizeException extends Exception
{

    /**
     * Constructor to throw this Exception with a custom message to display.
     * @param message A message to display.
     */
    public WrongSizeException(String message)
    {
        super(message);
    }
}
