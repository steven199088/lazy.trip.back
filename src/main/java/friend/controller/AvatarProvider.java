package friend.controller;

import member.model.Member;
import member.service.MemberServiceImpl;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

@WebServlet("/img/avatar.png")
public class AvatarProvider extends HttpServlet {

    // https://stackoverflow.com/questions/8623709/output-an-image-file-from-a-servlet
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String memberId = request.getParameter("member_id");
        response.setContentType("img/png");

        MemberServiceImpl service = null;
        byte[] imgBytes;
        try {
            service = new MemberServiceImpl();
            Member member = service.findById(Integer.parseInt(memberId));
            imgBytes = member.getImg();
            // 處理會員本身沒上傳大頭貼的情況
            if (imgBytes == null || imgBytes.length == 0) {
                // Use avatar png from GitHub: a man with beards
                InputStream fallbackAvatar = new URL("https://github.com/ortichon.png").openStream();
                imgBytes = fallbackAvatar.readAllBytes();
                fallbackAvatar.close();
            }

            // 輸出
            ByteArrayInputStream in = new ByteArrayInputStream(imgBytes);
            OutputStream out = response.getOutputStream();
            // Copy the contents of the file to the output stream
            byte[] buf = new byte[1024];
            int count = 0;
            while ((count = in.read(buf)) >= 0) {
                out.write(buf, 0, count);
            }
            out.close();
            in.close();

        } catch (NamingException e) {
            e.printStackTrace();
        }

    }
}
