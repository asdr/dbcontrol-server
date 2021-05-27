class DBOps extends React.Component {
	constructor(props) {
		super(props);
		this.state = {
		    version: '0.0.0.1a'
		};
	}
	render() {
	    return (
	      <div className="application dbops">
	        <Header auth={false} />
	        
	      </div>
	    );
	}
}

class Header extends React.Component {
	constructor(props) {
		super(props);
	}
	
	render() {
		let loginLogoutButton;
		if (this.props.auth) {
			loginLogoutButton = <a className="btn btn-min btn-outline-light btn-sm" href="#logout">Logout</a>;
		}
		else {
			loginLogoutButton = <a className="btn btn-min btn-outline-light btn-sm" href="#login">Login</a>;
		}
		return (
			<header className="navbar navbar-expand-lg navbar-dark bg-dark justify-content-between">
				<a className="navbar-brand" href="/dbops/ui/">DBOps Server Administration Console</a>
				{loginLogoutButton}
			</header>
		);
	}
}
