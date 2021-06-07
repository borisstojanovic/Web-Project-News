package rs.raf.Web_Project;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import rs.raf.Web_Project.repositories.category.ICategoryRepository;
import rs.raf.Web_Project.repositories.category.MySqlCategoryRepository;
import rs.raf.Web_Project.repositories.comment.ICommentRepository;
import rs.raf.Web_Project.repositories.comment.MySqlCommentsRepository;
import rs.raf.Web_Project.repositories.news.INewsRepository;
import rs.raf.Web_Project.repositories.news.MySqlNewsRepository;
import rs.raf.Web_Project.repositories.tag.ITagRepository;
import rs.raf.Web_Project.repositories.tag.MySqlTagRepository;
import rs.raf.Web_Project.repositories.user.IUserRepository;
import rs.raf.Web_Project.repositories.user.MySqlUserRepository;
import rs.raf.Web_Project.services.*;

import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("/api")
public class HelloApplication extends ResourceConfig {
    public HelloApplication() {
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);

        AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                this.bind(MySqlCommentsRepository.class).to(ICommentRepository.class).in(Singleton.class);
                this.bind(MySqlUserRepository.class).to(IUserRepository.class).in(Singleton.class);
                this.bind(MySqlCategoryRepository.class).to(ICategoryRepository.class).in(Singleton.class);
                this.bind(MySqlNewsRepository.class).to(INewsRepository.class).in(Singleton.class);
                this.bind(MySqlTagRepository.class).to(ITagRepository.class).in(Singleton.class);

                this.bindAsContract(CommentService.class);
                this.bindAsContract(UserService.class);
                this.bindAsContract(CategoryService.class);
                this.bindAsContract(NewsService.class);
                this.bindAsContract(TagService.class);
            }
        };
        register(binder);

        packages("rs.raf.Web_Project.resources.cms", "rs.raf.Web_Project.filters", "rs.raf.Web_Project.errorhandler");
    }
}