package com.twu.biblioteca;
import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;

public class WriterTest {
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
