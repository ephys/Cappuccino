package paoo.cappuccino.dal.dao;

public interface IContactDao {

  /**
   * Insert a new contact in the database
   *
   * @param contact The contact to insert
   * @return the contact entity with its informations update from the database
   * @throws java.lang.IllegalArgumentException                One of the fields failed to insert
   *                                                           due to constraint violations.
   * @throws paoo.cappuccino.dal.exception.ConnectionException Database connection error
   */
  //IContactDto createContact(IContactDto contact);

  /**
   * Fetches all contacts whos comany_id matche with the @param company
   *
   * @param company The contact's company
   * @return an arrayList with all contact find or a null arrayList if none was found
   * @throws paoo.cappuccino.dal.exception.ConnectionException Database connection error
   */
  //ArrayList<IContactDto> fetchContactsByCompany(int company);

  /**
   * Fetches all contacts that matche the firstName and/or the lastName
   *
   * @param firstName The firstname of the contact
   * @param lastName  The lastname of the contact
   * @return An arrayList of contact whi matche the firstName and/or the lastName or a null
   * arrayList if none was found
   * @throws paoo.cappuccino.dal.exception.ConnectionException Database connection error
   */
  //ArrayList<IContactDto> fetchContactByName(String firstName, String lastName);

}

