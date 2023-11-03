console.log("Running version 4.");

const ce = React.createElement

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

ReactDOM.render(
    ce('div', null, ce(Hello, {toWhat: 'World'}, null), ce(StatelessHello, {toWhat: 'Earth'}, null)), 
    document.getElementById('react-root')
);