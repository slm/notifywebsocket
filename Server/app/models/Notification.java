package models;

import play.data.validation.Constraints;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by pc on 18.02.2015.
 */
@Entity
public class Notification extends Model {

    @Id
    public String id;

    public String title;

    public String message;





}
