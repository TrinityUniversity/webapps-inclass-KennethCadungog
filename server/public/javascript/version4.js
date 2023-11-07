console.log("Running version 4.");

const ce = React.createElement
const csrfToken = document.getElementById("csrfToken").value;
const validateRoute = document.getElementById("validateRoute").value;
const tasksRoute = document.getElementById("tasksRoute").value;
const createRoute = document.getElementById("createRoute").value;
const deleteRoute = document.getElementById("deleteRoute").value;
const addRoute = document.getElementById("addRoute").value;
const logoutRoute = document.getElementById("logoutRoute").value;

class LoginComponent extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            loginName: "", 
            loginPass: "", 
            createName: "", 
            craetePass: "",
            loginMessage: "",
            createMessage: ""
        };
    }
    render() {
        return ce('div', null,
            ce('h2', null, 'Login:'),
            ce('br'),
            'Username: ',
            ce('input', {type: "text", id: "loginName", value: this.state.loginName, onChange: e => this.changeHandler(e)}),
            ce('br'),
            'Password: ',
            ce('input', {type: "password", id: "loginPass", value: this.state.loginPass, onChange: e => this.changeHandler(e)}),
            ce('br'),
            ce('button', {onclick: e => this.login(e)}, 'Login'),
            ce('span', {id: "login-message"}, this.state.loginMessage),
            ce('h2', null, 'Create User:'),
            ce('br'),
            'Username: ',
            ce('input', {type: "text", id: "createName", value: this.state.createName, onChange: e => this.changeHandler(e)}),
            ce('br'),
            'Password: ',
            ce('input', {type: "password", id: "createPass", value: this.state.createPass, onChange: e => this.changeHandler(e)}),
            ce('br'),
            ce('button', {onClick: e => this.createUser(e)}, 'Create User'),     
            ce('span', {id: "create-message"}, this.state.createMessage),
        );
    }

    changeHandler(e) {
        this.setState({ [e.target['id']]: e.target.value });
    }

    login(e) {
        const username = this.state.loginName;
        const password = this.state.loginPass;
        fetch(validateRoute, {
            method: 'POST',
            headers: {'Content-Type': 'applicatoin/json', 'Csrf-Token': csrfToken },
            body: JSON.stringify({ username, password })
        }).then(res => res.json()).then(date => {
          if(data) {
            //document.getElementById("login-section").hidden = true;
            //document.getElementById("task-section").hidden = false;
            //document.getElementById("login-message").innerHTML = "";
            //document.getElementById("create-message").innerHTML = "";
            //loadTasks();
          } else {
            this.setState({ loginMessage: "Login Failed" });
          }
        });
    }
}

ReactDOM.render(
    ce(LoginComponent, null, null),
    document.getElementById('react-root')
);



/*
function StatelessHello(props) {
    return ce('div', null, `Hello ${props.toWhat}`);
}

class Hello extends React.Component {
    constructor(props) {
        super(props);
        this.state = { clickCount: 0 };
    }
    render() {
        return ce('div', {onClick: (e) => this.clickHanlder(e)}, `Hello ${this.props.toWhat} - click count ${this.state.clickCount}`);
    }

    clickHanlder(e) {
        this.setState({clickCount: this.state.clickCount + 1});
    }
}

class SimpleForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {textInput: ""}
    }
    render() {
        return ce('input', {type: "text", value: this.state.textInput, onChange: (e) => this.changeHandler(e)});
    }

    changeHandler(e) {
        this.setState({textInput: event.target.value});
    }
}

ReactDOM.render(
    ce('div', null, 
        ce(Hello, {toWhat: 'World'}, null), 
        ce(StatelessHello, {toWhat: 'Earth'}, null), 
        ce(SimpleForm, null, null)
    ),
    document.getElementById('react-root')
);
*/