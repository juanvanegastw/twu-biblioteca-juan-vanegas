package com.twu.biblioteca.library;
import com.twu.biblioteca.library.book.Writer;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class WriterTest {

    @Test
    public void shouldCreateAWriterUsingItsName(){
        // Arrange
        String writerName = "Author";

        // Act
        Writer writer = new Writer(writerName);

        // Assert
        assertThat(writer.getName(), is(writerName));
    }
}
