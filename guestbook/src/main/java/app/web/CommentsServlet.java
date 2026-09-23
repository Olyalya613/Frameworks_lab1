package app.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class CommentsServlet extends HttpServlet {

    private static final Logger log =
            LoggerFactory.getLogger(CommentsServlet.class);

    private final CommentDao dao = new CommentDao();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp) throws IOException {

        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        try {
            var comments = dao.list();
            objectMapper.writeValue(resp.getWriter(), comments);

        } catch (Exception e) {
            log.error("Could not load comments", e);
            resp.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "DB error");
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp) throws IOException {

        req.setCharacterEncoding("UTF-8");

        String author = req.getParameter("author");
        String text = req.getParameter("text");

        if (author == null || author.trim().isEmpty()) {
            resp.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Author is required");
            return;
        }

        if (text == null || text.trim().isEmpty()) {
            resp.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Text is required");
            return;
        }

        author = author.trim();
        text = text.trim();

        if (author.length() > 64) {
            resp.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Author must be at most 64 characters");
            return;
        }

        if (text.length() > 1000) {
            resp.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    "Text must be at most 1000 characters");
            return;
        }

        try {
            long id = dao.add(author, text);

            log.info(
                    "New comment: id={}, author={}, length={}",
                    id, author, text.length());

            resp.setStatus(HttpServletResponse.SC_NO_CONTENT);

        } catch (Exception e) {
            log.error("Could not save comment", e);
            resp.sendError(
                    HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "DB error");
        }
    }
}
