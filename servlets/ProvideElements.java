/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package pl.polsl.marta.wower.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import pl.polsl.marta.wower.model.*;

/**
 * Servlet managing given matrix data.
 * @author Marta Wower
 * @version 1.0.0
 */
@WebServlet(name = "ProvideElements", urlPatterns = {"/ProvideElements"})
public class ProvideElements extends HttpServlet {
    /**
     * Access to model classes through a model object.
     */
    Model model;
    /**
     * Variable to calculate visits based on cookies.
     */
    int visitCounter = 0;
    /**
     * ArrayList storing history of calculations.
     */
    ArrayList<String> history = new ArrayList<>();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     * Displays result of calculation, gives the user a choice to go back to the start 
     * or display history of calculations.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try ( PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Result</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Result of the equation: " + "</h1>");
            out.println(displayMatrix(model.getCalculator().getResult()));
            
            Cookie[] cookies = request.getCookies();
            
            if(cookies == null) {
                out.println("<br>Hello, it looks like it's your first visit here!<br>Make yourself at home!<br>"); 
            }
            else {
                for(Cookie cookie: cookies) {
                   if(cookie.getName().equals("visit"))
                        visitCounter++;
                }
                
                out.println("<br>Looks like you came back!<br>You visited " + visitCounter + " times already!");
            }
            
            out.println("<br><br><form method='post' action='NextAction'>");
            out.println("<input type='submit' name='tryAgain' value='Try Again'>");
            out.println("<input type='submit' name='displayHistory' value='History'>");
            out.println("</form>");
            
            out.println("</body>");
            out.println("</html>");
            
            Cookie cookie = new Cookie("visit", ".");
            response.addCookie(cookie);
    }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * Passes data from input to matrices and calculates the result based on chosen button.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        model = new Model();
        if(request.getParameter("addition") != null || request.getParameter("subtraction") != null || request.getParameter("multiplication") != null) {
                //textarea to matrices - get sizes and put numbers into an array
                try {
                String elementsAInput = request.getParameter("elementsA");
                ArrayList<Integer> elementsAndSizeA = stringToArrayOfIntegers(elementsAInput);
                Matrix matrixA;
                
                String elementsBInput = request.getParameter("elementsB");
                ArrayList<Integer> elementsAndSizeB = stringToArrayOfIntegers(elementsBInput);
                Matrix matrixB;
                
                    matrixA = new Matrix(elementsAndSizeA.get(elementsAndSizeA.size() - 2), elementsAndSizeA.get(elementsAndSizeA.size() - 1));
                    matrixB = new Matrix(elementsAndSizeB.get(elementsAndSizeB.size() - 2), elementsAndSizeB.get(elementsAndSizeB.size() - 1));
                    
                    
                    //remove two last numbers (sizes)
                    for(int i = 0; i < 2; i++) {
                        elementsAndSizeA.remove(elementsAndSizeA.size() - 1);
                        elementsAndSizeB.remove(elementsAndSizeB.size() - 1);
                    }
                    
                    matrixA.fillMatrix(elementsAndSizeA);
                    matrixB.fillMatrix(elementsAndSizeB);
                    
                    String symbol = "";
                    
                    if(request.getParameter("addition") != null) {
                        model.setCalculator(new Calculator(matrixA, matrixB, "addition"));
                        symbol = "+";
                    }
                    else if(request.getParameter("subtraction") != null) {
                        model.setCalculator(new Calculator(matrixA, matrixB, "subtraction"));
                        symbol = "-";
                    }
                    else if(request.getParameter("multiplication") != null) {
                        model.setCalculator(new Calculator(matrixA, matrixB, "multiplication"));
                        symbol = "x";
                    }
               
                    HttpSession session = request.getSession();
                    session.getAttribute("history");
                    history.add("<br><br>" + displayMatrix(model.getCalculator().getMatrixA()) + "<br><br>" + symbol + "<br><br>" 
                            + displayMatrix(model.getCalculator().getMatrixB()) + "<br><br>=<br><br> " 
                            + displayMatrix(model.getCalculator().getResult()) + "<br><br>--------------<br><br>");
                    session.setAttribute("history", history);
                    
                    processRequest(request, response);
                    
            }  
        catch (WrongSizeException | UnavailableChoiceException ex) {
            response.sendError(response.SC_BAD_REQUEST, ex.getMessage());
        }
        

        
    }
    }
    
    /**
     * Changes matrix string to a HTML proper one (replaces \n with br)
     * @param matrix A matrix to be displayed
     * @return modified matrix string
     */
    private String displayMatrix (Matrix matrix) {
        String matrixText;
        
        matrixText = matrix.toString().replace("\n", "<br>");
        
        return matrixText;
    }
    
    /**
     * Creates an ArrayList of Integers from text area input (in form of string) to fill the matrix with that data later on.
     * Counts number of rows and columns.
     * @param text Input from text area.
     * @return ArrayList of Integers with elements and number of rows and columns at the end.
     * @throws WrongSizeException if any row is of different length than the rest.
     * @throws UnavailableChoiceException if any element is not a number.
     */
    private ArrayList<Integer> stringToArrayOfIntegers (String text) throws WrongSizeException, UnavailableChoiceException {
        //AT THE END - Row count, Column Count
        ArrayList<Integer> elements = new ArrayList<>();
        
        int rowCounter = 1;
        int[] columnCounters = new int[5];
        
        String longerNumber = "";
        boolean isLonger = false;
        boolean isInputCorrect = true;
         
        for(int i = 0; i < text.length(); i++) {
            Character character = text.charAt(i);
            
            if(character != ' ' && character != '\r') {
                if(character == '\n') {
                    rowCounter++;
                    columnCounters[rowCounter-1] = 0;
                }
                else {
                    
                    //check if it's a number
                    if(character > '9' || character < '0')
                        throw new UnavailableChoiceException("All elements must be numbers");
            
                    if(i == text.length() - 1 || text.charAt(i + 1) == ' ' || text.charAt(i + 1) == '\n' || text.charAt(i + 1) == '\r') {
                        if(!isLonger) {
                            elements.add(Character.getNumericValue(character));
                        }
                        else {
                            longerNumber += character;
                            elements.add(Integer.valueOf(longerNumber));
                            longerNumber = "";
                            isLonger = false;
                        }
                        
                        columnCounters[rowCounter-1]++;
                    }
                    else {
                        longerNumber += character;
                        
                        if(i == text.length() - 2 || text.charAt(i + 2) == ' ' || text.charAt(i + 2) == '\n' || text.charAt(i + 1) == '\r')
                            isLonger = true;
                    }
                }
            }
            
        }
        
        elements.add(rowCounter);
        //check if all columns are the same quantity
        for(int i = 1; i < rowCounter; i++) {
            if(columnCounters[0] != columnCounters[i])
                isInputCorrect = false;
        }
        
        if(isInputCorrect)
            elements.add(columnCounters[0]);
        else
            throw new WrongSizeException("All rows should have the same quantity of elements");
        
        return elements;
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
