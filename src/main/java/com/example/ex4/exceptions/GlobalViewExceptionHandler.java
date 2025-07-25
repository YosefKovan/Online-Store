package com.example.ex4.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.sql.SQLException;

/**
 * Global exception handler for MVC views, mapping various exceptions to user-friendly error pages.
 */
@ControllerAdvice
public class GlobalViewExceptionHandler {

    /**
     * Handles DataIntegrityViolationException by returning a conflict error view.
     *
     * @param model Spring MVC model to add error attributes
     * @return logical view name for the error page
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleConflict(Model model) {
        model.addAttribute("errorCode", HttpStatus.CONFLICT.value());
        model.addAttribute("errorMessage", "Data integrity violation");
        return "error";
    }

    /**
     * Handles any uncaught exceptions by returning a generic error view with status 500.
     *
     * @param ex    the exception that was thrown
     * @param model Spring MVC model to add error details
     * @return logical view name for the error page
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleGenericException(Exception ex, Model model) {
        model.addAttribute("errorCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }

    /**
     * Handles SQL and DataAccess exceptions by returning a database error view.
     *
     * @param model Spring MVC model to add error details
     * @param ex    the database-related exception
     * @return logical view name for the error page
     */
    @ExceptionHandler({SQLException.class, DataAccessException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleDatabaseErrors(Model model, Exception ex) {
        model.addAttribute("errorCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
        model.addAttribute("errorMessage", "Database error: " + ex.getMessage());
        return "error";
    }

    /**
     * Handles missing path variables by returning a bad request view.
     *
     * @param model Spring MVC model to add error details
     * @param ex    the MissingPathVariableException thrown
     * @return logical view name for the error page
     */
    @ExceptionHandler(MissingPathVariableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMissingPathVars(Model model, Exception ex) {
        model.addAttribute("errorCode", HttpStatus.BAD_REQUEST.value());
        model.addAttribute("errorMessage", "Missing path variable: " + ex.getMessage());
        return "error";
    }

    /**
     * Handles missing request parameters by returning a bad request view.
     *
     * @param model Spring MVC model to add error details
     * @param ex    the MissingServletRequestParameterException thrown
     * @return logical view name for the error page
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMissingRequestParams(Model model, Exception ex) {
        model.addAttribute("errorCode", HttpStatus.BAD_REQUEST.value());
        model.addAttribute("errorMessage", "Missing request parameter: " + ex.getMessage());
        return "error";
    }

    /**
     * Handles illegal arguments by returning a bad request view.
     *
     * @param model Spring MVC model to add error details
     * @param ex    the IllegalArgumentException thrown
     * @return logical view name for the error page
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleIllegalArgument(Model model, Exception ex) {
        model.addAttribute("errorCode", HttpStatus.BAD_REQUEST.value());
        model.addAttribute("errorMessage", "Invalid argument: " + ex.getMessage());
        return "error";
    }

    /**
     * Handles illegal states by returning a generic error view with status 500.
     *
     * @param model Spring MVC model to add error details
     * @param ex    the IllegalStateException thrown
     * @return logical view name for the error page
     */
    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleIllegalState(Model model, Exception ex) {
        model.addAttribute("errorCode", HttpStatus.INTERNAL_SERVER_ERROR.value());
        model.addAttribute("errorMessage", "Illegal state: " + ex.getMessage());
        return "error";
    }

    /**
     * Handles ResponseStatusException (e.g., not found errors) by returning a not found view.
     *
     * @param model Spring MVC model to add error details
     * @param ex    the ResponseStatusException thrown
     * @param req   the HttpServletRequest that triggered the exception
     * @return logical view name for the error page
     */
    @ExceptionHandler(ResponseStatusException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound(Model model, ResponseStatusException ex, HttpServletRequest req) {
        model.addAttribute("errorCode", HttpStatus.NOT_FOUND.value());
        model.addAttribute("errorMessage", ex.getReason());
        model.addAttribute("url", req.getRequestURL());
        return "error";
    }
}
