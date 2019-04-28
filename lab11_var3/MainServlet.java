import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// @WebServlet("/")
public class MainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String word = req.getParameter("word");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                getServletContext().getResourceAsStream("data.txt")))) {
            String line;
            int count = 0;
            boolean found = false;
            Pattern pattern = Pattern.compile(word);
            Matcher matcher;
            while ((line = reader.readLine()) != null) {
                matcher = pattern.matcher(line);
                while (matcher.find()) {
                    found = true;
                    count++;
                }
            }

            if (found) {
                req.setAttribute("answer", word);
                req.setAttribute("count", count);
            } else {
                req.setAttribute("answer", "No such word in file!");
            }
        } catch (FileNotFoundException | NullPointerException e) {
            req.setAttribute("answer", "File not found!");
        }

        req.getRequestDispatcher("search_response.jsp").forward(req, resp);
    }
}
