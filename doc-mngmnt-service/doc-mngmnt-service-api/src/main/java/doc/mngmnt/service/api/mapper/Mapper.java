package doc.mngmnt.service.api.mapper;

public interface Mapper<From, To> {

    To map(From from);
}
