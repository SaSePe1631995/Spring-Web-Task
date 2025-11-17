package web.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(DaoUserLayerException.class)
    public String handleDaoException(DaoUserLayerException e, Model model) {
        logger.error("Database error occurred");
        model.addAttribute("errorMessage", "Sorry, database operation failed");
        model.addAttribute("errorDetails", e.getMessage());
        return "error-dao-error";
    }
}
