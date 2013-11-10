Persistence Library [![Build Status](https://api.travis-ci.org/bednar/persistence.png?branch=master)](https://travis-ci.org/bednar/persistence)
====

## Persist Events

### Read

    @Inject
    private Dispatcher dispatcher;

    ...

    ReadEvent<Pub> event = new ReadEvent<Pub>(2L, Pub.class)
    {
        @Override
        public void success(@Nonnull final Pub value)
        {
            ...
        }
    };

    dispatcher.publish(event);

### Save

    @Inject
    private Dispatcher dispatcher;

    ...

    Pub pub = ...;

    dispatcher.publish(new SaveEvent(pub));

### List

    @Inject
    private Dispatcher dispatcher;

    ...

    ListEvent<Pub> event = new ListEvent<Pub>(Restrictions.like("name", "Irish%"), Pub.class)
    {
        @Override
        public void success(final @Nonnull List<Pub> pubs)
        {
            ...
        }
    };

    dispatcher.publish(event);

### Delete

    @Inject
    private Dispatcher dispatcher;

    ...

    DeleteEvent event = new DeleteEvent(pub);

    dispatcher.publish(event);

## Maven Repository

    <repository>
        <id>public</id>
        <name>Public</name>
        <url>http://nexus-bednar.rhcloud.com/nexus/content/groups/public/</url>
    </repository>

## License

    Copyright (c) 2013, Jakub Bednář
    All rights reserved.

    Redistribution and use in source and binary forms, with or without modification,
    are permitted provided that the following conditions are met:

    * Redistributions of source code must retain the above copyright notice, this
      list of conditions and the following disclaimer.

    * Redistributions in binary form must reproduce the above copyright notice, this
      list of conditions and the following disclaimer in the documentation and/or
      other materials provided with the distribution.

    THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
    ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
    WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
    DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
    ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
    (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
    LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
    ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
    (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
    SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
