import org.apache.commons.io.FileUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.junit.Test;

import java.io.File;

/**
 * @ProjectName lucene
 * @ClassName demo
 * @Description TODO
 * @Author boos
 * @Date 2018/10/17 18:15
 * @Version 1.0
 **/
public class demo {

    @Test
    public  void index()throws  Exception{
        //指定一个路径(索引库)
        String path = "E:/Y2/lucene";
        //创建目录存储索引
        Directory directory= FSDirectory.open(new File(path));
        //创建一个分词器，用于分词
        Analyzer analyzer=new StandardAnalyzer();
        //创建一个IndexWriterConfig对象，用于指定indexWriter的一些信息
        IndexWriterConfig indexWriterConfig=new IndexWriterConfig(Version.LATEST,analyzer);
        //创建IndexWriter，用于解析文档，产生索引
        IndexWriter indexWriter=new IndexWriter(directory,indexWriterConfig);
        //读取某一个目录下面的所有文件，准备创建索引----------------------读取的是目录
        File src = new File("E:/Y2/Demo");
        File[] files = src.listFiles();//获得该目录下面的全部文件
        //对每一个文件循环遍历，准备拆分词汇，创建出索引
        for(File f:files){

            String name = f.getName();//文件名
            String pth = f.getPath();//文件的保存路径
            long size = FileUtils.sizeOf(f);//当前文件的大小
            String  content = FileUtils.readFileToString(f);

            //对每一个文档，进行分析，得到每一个文档：  名称，大小，保存路径，文件内容,然后把这些信息保存成一个Document对象，写入索引库
            Document doc = new Document();

            //创建Field(字段)，这个Field可以包含一些信息，这些信息就索引检索的关键字
            Field tname = new TextField("tname", name, Field.Store.YES);
            Field tcontent =new TextField("tcontent", content, Field.Store.YES);
            Field tsize = new LongField("tsize", size, Field.Store.YES);
            Field tpath = new StoredField("tpath",pth);

            doc.add(tname);
            doc.add(tcontent);
            doc.add(tsize);
            doc.add(tpath);

            indexWriter.addDocument(doc);//输出索引
        }
        indexWriter.close();;
        System.out.println("索引创建成功");

    }
}
