package cn.edu.sdu.drs.util.converter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.converter.PicturesManager;
import org.apache.poi.hwpf.converter.WordToHtmlConverter;
import org.apache.poi.hwpf.usermodel.Picture;
import org.apache.poi.hwpf.usermodel.PictureType;

/**功能：word->html
 * @author msz
 *     <br>输入参数：源文件路径、输出目录路径(末尾以"/"结尾)、输出文件名
 *  <br>输出参数：true|false(转换成功则为true，否则为false)
 */
public class Word2Html {
    
    public static boolean convert(final String srcFilePath,final String targetDir,final String htmName){
        
        try {
            InputStream is = new FileInputStream(srcFilePath);
            HWPFDocument wordDocument = new HWPFDocument(is);//src.doc->HWPFDocument
            WordToHtmlConverter wordToHtmlConverter = new WordToHtmlConverter(DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument());
            //图片的设置
            wordToHtmlConverter.setPicturesManager(new PicturesManager() {
                public String savePicture(byte[] content,
                        PictureType pictureType, String suggestedName,
                        float widthInches, float heightInches) {
                    return suggestedName;
                }
            });
            wordToHtmlConverter.processDocument(wordDocument);
            List<Picture> pics = wordDocument.getPicturesTable()
                    .getAllPictures();
            if (pics != null) {
                for (int i = 0; i < pics.size(); i++) {
                    Picture pic = (Picture) pics.get(i);
                    try {
                        pic.writeImageContent(new FileOutputStream(targetDir
                                + pic.suggestFullFileName()));
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
            
            Document htmlDocument = wordToHtmlConverter.getDocument();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DOMSource domSource = new DOMSource(htmlDocument);
            StreamResult streamResult = new StreamResult(baos);

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer serializer = tf.newTransformer();
            serializer.setOutputProperty(OutputKeys.ENCODING, "gbk");
            serializer.setOutputProperty(OutputKeys.INDENT, "yes");
            serializer.setOutputProperty(OutputKeys.METHOD, "html");
            serializer.transform(domSource, streamResult);
            baos.close();

            String content = new String(baos.toByteArray());

            FileUtils.writeStringToFile(new File(targetDir, htmName + ".html"),
                    content, "gbk");
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }
}