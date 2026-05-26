/*
 * This program and the accompanying materials are made available under the terms of the
 * Eclipse Public License v2.0 which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-v20.html
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Copyright Contributors to the Zowe Project.
 */
package org.zowe.apiml.client.model.graphql;

import org.zowe.apiml.client.exception.BookAlreadyExistsException;
import org.zowe.apiml.client.exception.BookNotFoundException;
import java.util.*;

public record Book(String bookId, String name, Integer pageCount, String authorId) {

    private static List<Book> books = new ArrayList<>(List.of(new Book("book-1", "Effective Java", 0, "author-1"), new Book("book-2", "Hitchhiker's Guide to the Galaxy", 208, "author-2"), new Book("book-3", "Down Under", 436, "author-3")));

    public static List<Book> getAllBooks() {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static Book getBookById(String bookId) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static Book getById(String bookId) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static Book addBook(String name, Integer pageCount, String authorId) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static Book updateBook(String bookId, String name, Integer pageCount, String authorId) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static Book deleteBook(String bookId) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }

    public static void setBooks(List<Book> newBooks) {
        throw new UnsupportedOperationException("STUB: not implemented");
    }
}
