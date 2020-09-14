package com.vilect.asm.util;

import com.vilect.asm.model.NewsModel;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import static com.vilect.asm.LibraryClass.newsArrayList;

public class NewsSaxHandler extends DefaultHandler {

    //tạo đối tượng News để lưu trữ dữ liệu lấy đc từ RSS
    private NewsModel newsModel = new NewsModel();
    private String data;

    //các biến để xác định đang quét đến đâu
    private boolean isTitle = false;
    private boolean isDes = false;
    private boolean isPub = false;
    private boolean isLink = false;


    //khi gặp thẻ mở thì hàm này được gọi
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        super.startElement(uri, localName, qName, attributes);

        //Khi gặp thẻ item
        if (qName.equalsIgnoreCase("item"))
        {
            newsModel = new NewsModel();
        }
        else
        {
            //khi gặp thẻ title
            if (qName.equalsIgnoreCase("title"))
            {
                //khi gặp thẻ mở title, thì chuyển isTitle = true để báo cho character biết
                isTitle = true;
            }
            else
            {
                if (qName.equalsIgnoreCase("description"))
                {
                    //khi gặp thẻ mở description, thì chuyển isDes = true để báo cho character biết
                    isDes = true;
                }
                else
                {
                    if (qName.equalsIgnoreCase("pubDate"))
                    {
                        //khi gặp thẻ mở pubDate, thì chuyển isTitle = true để báo cho character biết
                        isPub = true;
                    }
                    else
                    {
                        if (qName.equalsIgnoreCase("link"))
                        {
                            //khi gặp thẻ mở link, thì chuyển isTitle = true để báo cho character biết
                            isLink = true;
                        }
                    }
                }
            }
        }
    }


    //hàm này được gọi sau khi hàm startElement gặp thẻ mở, dùng để lấy nội dung ở bên trong thẻ
    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        super.characters(ch, start, length);

        //lấy dâta
        data = new String(ch, start, length);

        //set data vào thuộc tính phù hợp
        //sau đó chuyển biến boolean thành false để đợi lần quét tiếp theo

        if (isTitle)
        {
            newsModel.setTitle(data);
            isTitle = false;
        }
        if (isDes)
        {
            newsModel.setDes(data);
            isDes = false;
        }
        if (isPub)
        {
            newsModel.setPubDate(data);
            isPub = false;
        }
        if (isLink)
        {
            newsModel.setLink(data);
            isLink = false;
        }

    }


    //hàm này được gọi khi quét đến thẻ đóng nào đó
    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        super.endElement(uri, localName, qName);

        //gặp thẻ đóng item nghĩa là đã add hết các thẻ con r
        //nên add dối tượng newsModel vào newsArrayList luôn
        if (qName.equalsIgnoreCase("item"))
        {
            newsArrayList.add(newsModel);
        }

    }


}
