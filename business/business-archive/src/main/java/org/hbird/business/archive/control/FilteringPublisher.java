package org.hbird.business.archive.control;

import org.hbird.business.api.IPublisher;
import org.hbird.business.api.impl.AbstractHbirdApi;
import org.hbird.exchange.core.EntityInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FilteringPublisher extends AbstractHbirdApi implements IPublisher {
    private Logger LOG = LoggerFactory.getLogger(FilteringPublisher.class);

    protected IPublisher delegate;
    protected Filter filter;
        
    public FilteringPublisher(IPublisher delegate, Filter filter) {
        super(delegate.getIssuedBy(), delegate.getDestination());
        
        this.delegate = delegate;
        this.filter = filter;
    }

    @Override
    public void commit(String ID) throws Exception {

    }

    @Override
    public EntityInstance publish(EntityInstance object) throws Exception {
        if(filter.passes(object)) {
            LOG.trace(object + " passed the filter"); 
            return delegate.publish(object);
        } else {
            LOG.trace(object + " didn't pass the filter");
            return object;
        }
    }

}
