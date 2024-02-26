package by.bulaukin.bookshelf.servise.impl;

import by.bulaukin.bookshelf.entity.BooksEntity;
import by.bulaukin.bookshelf.exceptions.EntityNotFoundException;
import by.bulaukin.bookshelf.repository.BookRepository;
import by.bulaukin.bookshelf.repository.BooksEntitySpecification;
import by.bulaukin.bookshelf.servise.BooksService;
import by.bulaukin.bookshelf.web.model.filter.EntityFilter;
import by.bulaukin.bookshelf.web.model.request.UpsertAuthorAndBookNameRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BooksServiceImpl implements BooksService {

    private final BookRepository repository;

    @Override
    public BooksEntity findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(
                MessageFormat.format("Book by id {0} was not found", id)));
    }

    @Override
//    @CategoryChecker
    public BooksEntity createBookEntity(BooksEntity book) {
        return repository.save(book);
    }

    @Override
    public BooksEntity getByAuthorAndName(UpsertAuthorAndBookNameRequest request) {
        BooksEntity probe = new BooksEntity();
        probe.setAuthor(request.getAuthor());
        probe.setNamedBook(request.getNamedBook());

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnoreNullValues()
                .withIgnorePaths("id",
                        "category",
                        "createdAt",
                        "updatedAt");
        Example<BooksEntity> example = Example.of(probe, matcher);
        return repository.findOne(example).orElseThrow(() -> new EntityNotFoundException(MessageFormat
                .format("Book by bookName: {0} and author: {1} not found!",
                        request.getNamedBook(),
                        request.getAuthor())));
    }

    @Override
    public List<BooksEntity> getAllByCategory(EntityFilter filter) {
        return repository.findAll(BooksEntitySpecification.withFilter(filter));
    }

    @Override
    @SneakyThrows
    public BooksEntity update(BooksEntity book) {
        BooksEntity updatedBook = findById(book.getId());
        BeanUtils.copyProperties(updatedBook, book);
        return repository.save(book);
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
