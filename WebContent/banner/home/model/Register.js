Ext.define('home.model.Register', {
	extend : 'Ext.data.Model',
	fields : [ 'firstName', 'lastName', 'website', 'email', 'company',
			'phoneNumber', 'password', 'retypePassword' ],
	proxy : {
		url : rootContext + '/admin/getMemberProfile.json',
		type : 'ajax',
		reader : {
			type : 'json',
			root : 'result'
		}
	},
	 validations: [
	               {type: 'presence',  field: 'firstName'},
	               {type: 'presence',  field: 'lastName'},
	               {type: 'presence',  field: 'email'},
	               {type: 'presence',  field: 'password'},
	               {type: 'presence',  field: 'retypePassword'},
	               {type: 'length',    field: 'name',     min: 2},
	               {type: 'inclusion', field: 'gender',   list: ['Male', 'Female']},
	               {type: 'exclusion', field: 'username', list: ['Admin', 'Operator']},
	               {type: 'format',    field: 'username', matcher: /([a-z]+)[0-9]{2,3}/}
	           ]
});