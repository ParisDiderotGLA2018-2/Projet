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
var obj = {
    name: 'Horace',
    username: "Pifi",
};
var obj2 = {
    name: 'Popo',
    username: "damiLeBoss",
};
var obj3 = {
    name: 'Jj',
    username: "d4rkl1nk",
};


var testObject = _.template($('#test02').html());

var html = testObject(obj);
var html2 = testObject(obj2);
var html3 = testObject(obj3);

$('#result').append(html);
$('#result').append(html2);
$('#result').append(html3);



