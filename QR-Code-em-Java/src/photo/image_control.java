package photo;
import java.io.*;
import java.net.URL;
import Chatting.*;
public class image_control {
    public static void saveImage(String strUrl) throws IOException {
        CleanUp_folder("QR-Code-em-java/src/input");
        CleanUp_folder("QR-Code-em-java/src/output");

        URL url = null;
        InputStream in = null;
        OutputStream out = null;

        try {

            url = new URL(strUrl);

            in = url.openStream();

            out = new FileOutputStream("QR-Code-em-java/src/output/Photo.jpg"); //저장경로

            while(true){
                //이미지를 읽어온다.
                int data = in.read();
                if(data == -1){
                    break;
                }
                //이미지를 쓴다.
                out.write(data);

            }

            in.close();
            out.close();

        } catch (Exception e) {

            e.printStackTrace();

        }finally{

            if(in != null){in.close();}
            if(out != null){out.close();}

        }
    }

    private static void CleanUp_folder( String strDirPath ) {

        File path = new File(strDirPath);
        File[] fList = path.listFiles();

        for (int i = 0; i < fList.length; i++) {
            if (fList[i].isFile()) {
                if(fList[i].delete()) {
                    System.out.println(fList[i].getPath() + " 파일이 삭제되었습니다.");
                } else {
                    System.out.println(fList[i].getPath() + " 파일 삭제 실패.");
                }
            } else if (fList[i].isDirectory()) {
                CleanUp_folder(fList[i].getPath());
            }
        }
    }
}




