package losnazar.task.mapper;

public interface ResponseMapper<D, M> {
    D toDto(M model);
}
