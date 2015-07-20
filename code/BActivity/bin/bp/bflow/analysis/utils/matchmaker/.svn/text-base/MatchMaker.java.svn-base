package bp.bflow.analysis.utils.matchmaker;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;


/**
 * A matchmaker pattern implementation. Registers handlers by class
 * in a statically type safe way.
 *
 * @param <BaseClass>  Base class for the input objects.
 * @param <Handler> The objects that will handles instances of {@code Source}.
 * @author Michael Bar-Sinai mich.barsinai@gmail.com
 */
public class MatchMaker<BaseClass, Handler extends HandlerOf<? super BaseClass> > {

    /**
     * The Class->Handler map the client code specified. We need this to find the appropriate
     * handlers for classes we have'nt yet met.
     */
    private Map<Class<?>, Object> explicitHandlers = new HashMap<Class<?>, Object>();

    /** Cache for previous BFS run results, stored in a Class->Handler map. */
    protected Map<Class<?>, Object> handlerCache = new HashMap<Class<?>, Object>();
	
    /**
     * Sets a handler for a specific java class (or interface).
     * @param aClass the class for which we set the renderer
	 * @param aHandler the instance that will handle instances of the {@code aClass}.
     */
    public <T extends BaseClass> void registerHandler(Class<T> aClass, HandlerOf<? super T> aHandler) {
        if (aHandler != null) {
            handlerCache.put(aClass, aHandler);
            explicitHandlers.put(aClass, aHandler);

        } else {
            explicitHandlers.remove(aClass);
            handlerCache.clear();
        }
    }


    @SuppressWarnings("unchecked")
	public Handler match(BaseClass value) {
        Handler matchedHandler = null;
        if (value != null) {
            // Try to get the handler from the cache
            Class<?> valueClass = value.getClass();
            matchedHandler = (Handler) handlerCache.get(valueClass);
            if (matchedHandler == null) {
                // Cache miss. Find the the proper handler for the class using BFS.
                matchedHandler = getExplicitHandler(valueClass);
                handlerCache.put(valueClass, matchedHandler);
            }
        }

        return matchedHandler;
    }

    /**
     * Returns a "most appropriate" handler for instances of {@code valClass}
     * We do a BFS run over the super-classes and interfaces of valClass, considering
     * the interfaces <em>before</em> the super class for each visited class.
     *
     * @param valClass the class whose super types we want to list
     * @return handler specified to one of valClass' superclasses
     */
    @SuppressWarnings("unchecked")
	protected Handler getExplicitHandler(Class<?> valClass) {
        Queue<Class<?>> queue = new LinkedList<Class<?>>(); // the BFS' "to be visited" queue
        Set<Class<?>> visited = new HashSet<Class<?>>();    // the class objects we have visited

        queue.add(valClass);
        visited.add(valClass);

        while (!queue.isEmpty()) {
            Class<?> curClass = queue.remove();

            // get the super types to visit.
            List<Class<?>> supers = new LinkedList<Class<?>>();
            for (Class<?> itrfce : curClass.getInterfaces()) {
                supers.add(itrfce);
            }
            Class<?> superClass = curClass.getSuperclass(); // this could be null for interfaces.
            if (superClass != null) {
                supers.add(superClass);
            }

            for (Class<?> ifs : supers) {
                if (explicitHandlers.containsKey(ifs)) {
                    return (Handler) explicitHandlers.get(ifs);
                }
                if (!visited.contains(ifs)) {
                    queue.add(ifs);
                    visited.add(ifs);
                }
            }
        }
        return (Handler) explicitHandlers.get(Object.class);
    }
}
