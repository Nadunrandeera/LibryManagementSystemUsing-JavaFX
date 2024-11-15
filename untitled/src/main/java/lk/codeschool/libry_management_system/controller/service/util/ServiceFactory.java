package lk.codeschool.libry_management_system.controller.service.util;

import lk.codeschool.libry_management_system.controller.repo.CrudRepository;
import lk.codeschool.libry_management_system.controller.repo.util.RepoFactory;
import lk.codeschool.libry_management_system.controller.repo.util.RepoTypes;
import lk.codeschool.libry_management_system.controller.service.SuperService;
import lk.codeschool.libry_management_system.controller.service.custom.*;
import lk.codeschool.libry_management_system.controller.service.custom.impl.*;

public class ServiceFactory {
    private static final RepoFactory repoFactory = RepoFactory.getInstance();
    private static final OtherDependecies otherDependencies = OtherDependecies.getInstance();
    private static final ServiceFactory getInstance = new ServiceFactory();

    private final BookService bookService;
    private final AuthorService authorService;
    private final PublisherService publisherService;
    private final CategoryService categoryService;
    private final MemberService memberService;

    private ServiceFactory(){
        bookService = new BookServiceIMPL(otherDependencies.getMapper(),repoFactory.getRepo(RepoTypes.BOOK_REPO),repoFactory.getRepo(RepoTypes.BOOK_AUTHOR_REPO));
        authorService = new AuthorServiceIMPL(otherDependencies.getMapper(),repoFactory.getRepo(RepoTypes.AUTHOR_REPO));
        publisherService = new PublisherServiceIMPL(otherDependencies.getMapper(),repoFactory.getRepo(RepoTypes.PUBLISHER_REPO));
        categoryService = new CategoryServiceIMPL(otherDependencies.getMapper(),repoFactory.getRepo(RepoTypes.CATEGORY_REPO));
        memberService = new MemberServiceIMPL(otherDependencies.getMapper(),repoFactory.getRepo(RepoTypes.MEMBER_REPO));
    }

    public static ServiceFactory getInstance(){
        return getInstance;
    }

    public SuperService getService(ServiceTypes type){
        return switch (type){
            case BOOK_SERVICE -> bookService;
            case AUTHOR_SERVICE -> authorService;
            case CATEGORY_SERVICE -> categoryService;
            case MEMBER_SERVICE -> memberService;
            case PUBLISHER_SERVICE -> publisherService;
        };
    }
}
