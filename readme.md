What we want is:
- be able to define a Java type and its associated schema properties.
This is expressed:

JavaType | JSON Type | Custom Properties | Strategy
| ------------- |:-------------:| -----:| --- |
| `ZonedDateTime`  | `String` | `temporalFormat` | hard coded `"zonedDateTime"`
| `LocalDateTime`  | `String` | `temporalFormat` | hard coded `"localDateTime"`
| `LocalDate`  | `String` | `temporalFormat` | hard coded `"localDate"`

The Strategy would need to be a callback, ideally a SAM interface which provides a schema object for post-processing.  This means that the schema object has already been created before it is passed to the callback.  For the time being, we will restrict custom mapping to the provided `X<Type>Schema` schemas.

- be able to easily use this configured visitor to obtain a schema.
- be able to reliably print the JSON for the schema produced.
- this will preferably use the same `ObjectMapper` as the schema creation.
  -  _**however**_: if this is not possible, it needs to be easy to get a printed schema.
