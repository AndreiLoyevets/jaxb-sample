package com.aloievets.jaxb;

import com.aloievets.jaxb.book.BookForm;
import com.aloievets.jaxb.book.BooksForm;
import com.aloievets.jaxb.book.ObjectFactory;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.xml.bind.*;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

/**
 * Created by Andrew on 19.05.2017.
 */
public class Launcher {

    public static void main(String[] args) throws JAXBException, FileNotFoundException {
        JAXBContext context = JAXBContext.newInstance("com.aloievets.jaxb.book");

        readFromFileSample(context);
        writeToFileSample(context);
    }

    public static void readFromFileSample(JAXBContext context) throws JAXBException, FileNotFoundException {
        Unmarshaller unmarshaller = context.createUnmarshaller();

        System.out.println("Unmarshalling...");
        JAXBElement<BooksForm> jaxbElement = (JAXBElement<BooksForm>) unmarshaller.unmarshal(
                new FileInputStream("src/main/resources/books.xml"));
        System.out.println("Unmarshalling done!");

        BooksForm booksForm = jaxbElement.getValue();
        List<BookForm> booksFormList = booksForm.getBook();

        System.out.println("Read " + booksFormList.size() + " book records from file:");
        booksFormList.stream().map(ReflectionToStringBuilder::toString).forEach(System.out::println);
    }

    public static void writeToFileSample(JAXBContext context) throws JAXBException, FileNotFoundException {
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_ENCODING, "UTF-8");
        marshaller.setProperty(javax.xml.bind.Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        BookForm bookForm = new BookForm();
        bookForm.setAuthor("Me");
        bookForm.setGenre("Comedy");
        bookForm.setId("007");
        bookForm.setPrice(9.95f);
        bookForm.setTitle("My Life");
        bookForm.setReview("5 stars!!!");

        BooksForm booksForm = new BooksForm();
        booksForm.getBook().add(bookForm);

        ObjectFactory objectFactory = new ObjectFactory();
        JAXBElement<BooksForm> jaxbElement = objectFactory.createBooks(booksForm);

        System.out.println("Marshalling...");
        marshaller.marshal(jaxbElement, new FileOutputStream("target/generated-books.xml"));
        System.out.println("Marshalling done");
    }
}