package be.ictdynamic.dynarouteservice.common.collections.diff;

import org.apache.commons.collections15.Closure;
import org.apache.commons.collections15.CollectionUtils;

import java.util.Collection;

/**
 * Class DiffVisitor.
 *
 * @author Erik Verkelkeren
 * @version $Revision$
 * @since Oct 27, 2009
 * @param <T> The type of element
 */
public class DiffVisitor<T> {
    private final DiffEquals<T> equals;
    private final DiffHandler<T> handler;

    public DiffVisitor(DiffEquals<T> equals, DiffHandler<T> handler) {
        this.equals = equals;
        this.handler = handler;
    }

    public void visit(Collection<T> oldCollection, Collection<T> newCollection) {
        CollectionDiff<T> diff = new CollectionDiff<T>(oldCollection, newCollection, getEquals());

        CollectionUtils.forAllDo(diff.getAdded(), new Closure<T>() {
            public void execute(T t) {
                getHandler().doAdd(t);
            }
        });
        CollectionUtils.forAllDo(diff.getRemoved(), new Closure<T>() {
            public void execute(T t) {
                getHandler().doRemove(t);
            }
        });
        CollectionUtils.forAllDo(diff.getSame(), new Closure<T>() {
            public void execute(T t) {
                getHandler().doSame(t);
            }
        });
    }

    private DiffEquals<T> getEquals() {
        return equals;
    }

    private DiffHandler<T> getHandler() {
        return handler;
    }
}
