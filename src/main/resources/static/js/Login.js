
class Login extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      version: "1.0.0.0",
      page: "login.html",
      authenticated: false,
      
    };
  }

  render() {
    return (
      <div className="dbops pages.login">
        <Header auth={false} />
        <div>
          <form action="login" method="post">
            <input type="email" id="username" name="username"></input>
            <input type="password" id="password" name="password"></input>
            <input type="submit" value="Login"></input>
          </form>
        </div>
      </div>
    );
  }
}

// ========================================



