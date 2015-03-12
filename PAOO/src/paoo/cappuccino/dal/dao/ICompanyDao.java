package paoo.cappuccino.dal.dao;

public interface ICompanyDao {

  /**
   * Inserts a new comany in the database.
   *
   * @param company The company to insert
   * @return The company entity with its informations updated from the database
   * @throws paoo.cappuccino.dal.exception.NonUniqueFieldException The name is not unique.
   * @throws java.lang.IllegalArgumentException                    One of the fields failed to
   *                                                               insert due to constraint
   *                                                               violations.
   * @throws paoo.cappuccino.dal.exception.ConnectionException     Database connection error
   */
  //ICompanyDto createCompany(ICompanyDto company);

  /**
   * Fetche all the participations about the day in the database
   *
   * @param BusinessDay The day who we want the participation about (pas très anglais ma phrase)
   * @return An that arrayList contains all the participation or null if none was found.
   * @throws paoo.cappuccino.dal.exception.ConnectionException Database connection error
   */
  //ArrayList<IParticipation> fetchParticipationByDate(BusinessDay businessDay);

  /**
   * Fetche all the company matching all the not-null param.
   *
   * @param name     The name of the company
   * @param postcode The postcode of the adress of the company
   * @param street   The street of the adress of the company
   * @param town     The town of the adress of the company
   * @return An arrayList that contains all the company or null if none was found.
   * @throws paoo.cappuccino.dal.exception.ConnectionException Database connection error
   */
  //ArrayList<ICompanyDto> fetchCompanyByName(String name, String postcode, String street,
  //                                         String town);

}
