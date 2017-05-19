package com.aloievets.jaxb;

import com.aloievets.jaxb.book.BookForm;
import com.aloievets.jaxb.book.BooksForm;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * Created by Andrew on 19.05.2017.
 */
public class Launcher {

    public static void main(String[] args) throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance("com.aloievets.jaxb.book");
        Unmarshaller unmarshaller = context.createUnmarshaller();

        JAXBElement jaxbElement = (JAXBElement) unmarshaller.unmarshal(
                new FileInputStream("src/main/resources/books.xml"));

        BooksForm booksForm = (BooksForm) jaxbElement.getValue();
        List<BookForm> booksFormList = booksForm.getBook();

        booksFormList.stream().map(ReflectionToStringBuilder::toString).forEach(System.out::println);
    }
}