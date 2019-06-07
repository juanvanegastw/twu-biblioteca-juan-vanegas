package com.twu.biblioteca;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class WriterTest {

    /**
     * Puede ser mejor si solo lo llamas shouldCreateAWriterUsingItsName
     */
    @Test
    public void shouldAllowToCreateAuthorWithItsName(){
        // Arrange
        String writerName = "Author";

        // Act
        Writer writer = new Writer(writerName);

        // Assert
        assertThat(writer.getName(), is(writerName));
    }
}
