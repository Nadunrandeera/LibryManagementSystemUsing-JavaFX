package lk.codeschool.libry_management_system.controller.repo.util;

import lk.codeschool.libry_management_system.controller.repo.CrudRepository;
import lk.codeschool.libry_management_system.controller.repo.custom.*;
import lk.codeschool.libry_management_system.controller.repo.custom.impl.*;
import lk.codeschool.libry_management_system.controller.service.custom.impl.BookAuthorRepoIMPL;

//Factory design pattern
//singleton
public class RepoFactory {
    private static final RepoFactory instance = new RepoFactory();

    private final AuthorRepo authorRepo;
    private final BookRepo bookRepo;
    private final CategoryRepo categoryRepo;
    private final MemberRepo memberRepo;
    private final PublisherRepo publisherRepo;
    private final BookAuthorRepo bookAuthorRepo;

    //constructor private(call once in hole project) - 1
    private RepoFactory(){
        authorRepo = new AuthorRepoIMPL(); // just a one time create this objects
        bookRepo = new BookRepoIMPL();
        categoryRepo = new CategoryRepoIMPL();
        memberRepo = new MemberRepoIMPL();
        publisherRepo = new PublisherRepoIMPL();
        bookAuthorRepo = new BookAuthorRepoIMPL();
    }
    public <T extends CrudRepository>T getRepo(RepoTypes type){
        switch (type){
            case BOOK_REPO : return (T) this.bookRepo;
            case AUTHOR_REPO: return (T) this.authorRepo;
            case CATEGORY_REPO: return (T) this.categoryRepo;
            case MEMBER_REPO: return (T) this.memberRepo;
            case PUBLISHER_REPO: return (T) this.publisherRepo;
            case BOOK_AUTHOR_REPO: return (T) this.bookAuthorRepo;
            default:return null;
        }
    }

    public static RepoFactory getInstance(){
        return instance;
    }
}
