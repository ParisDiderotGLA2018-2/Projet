// JavaScript source code


/*this.object = {
    thumbnail: 'asfasdfasdf.jpg',
    name: 'jordan',
    city: 'toronto',
    summary: 'this is an example'
};

// Gets what is inside of the JS template and treats is as html
// stores the js template function as this.tmpl
// replaces the template placeholders with what is passed in the object
this.tmpl = _.template($('#Test01').html());
this.$element.append(this.tmpl(this.object));
*/

var testObject = _.template($('#test02').html());

var html = testObject({
    "attribute": JSON.stringify(result)
});

$('#result').append(html);


