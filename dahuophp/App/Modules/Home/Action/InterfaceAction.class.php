<?php
// 本文档自动生成，仅供测试运行
class IndexAction extends Action {
	/**
    +----------------------------------------------------------
	 * 默认操作
    +----------------------------------------------------------
	 */
	public function index() {
		
		switch ($_SERVER ['REQUEST_METHOD']) {
			case 'GET' :
				$the_request = &$_GET;
				break;
			case 'POST' :
				$the_request = &$_POST;
				break;
			case 'PUT' :
				$the_request = &$_PUT;
				break;
			case 'HEAD' :
				$the_request = &$_HEAD;
				break;
			default :
		}
		//dump('$_SERVERREQUEST_METHOD='.$_SERVER['REQUEST_METHOD'].' \n');
		$do = $the_request['do'];

		
		//dump('do ==='.$do. '  \n');
		switch ($do) {
			case 'login' :
				//dump('loginCheck \n');
				$data = $this->loginCheck ( $the_request );
				//$data ['userId'] = $uid;
				print (json_encode ( $data )) ;
				break;
			default :
				dump('ddefault  \n');
/*				$data = $this->getWeibo ( $the_request );
				print (json_encode ( $data )) ;*/
				$list = M ( 'info' )->select();	
				print (json_encode ( $list )) ;			
				break;
		}		
	}
	public function getNotifyNumber($the_request){
		$uid = $the_request ['uid'];
		//dump($uid);
		$offerid = M('notify')->where('receive='.$uid.' AND is_read=0')->count();
		return $offerid;
	}
	public function clearNotifyNumber($the_request){
		$uid = $the_request ['uid'];
		$map['receive'] = $uid;
		$offerid = M('Notify')->data(array('is_read'=>1))->where($map)->save();
		return $offerid;
	}	
	//保存offfer
	public function saveOffer($the_request) {
		//dump('function saveOffer');
		$data ['uid'] = $the_request ['uid'];
		$data ['money'] = $the_request ['money'];
		$data ['product'] = $the_request ['product'];
		$data ['ctime'] = $the_request ['ctime'];
		$data ['deadline'] = $the_request ['deadline'];
		$data ['longitude'] = $the_request ['longitude'];
		$data ['latitude'] = $the_request ['latitude'];
		$data ['needauction'] = $the_request ['needauction'];
		$data ['reason'] = $the_request ['reason'];
		//dump($data);
		$offerid = M ( 'Offer' )->add ( $data );
		return $offerid;
	}

	//查询offfer
	public function getOffer($the_request) {
		$gettype = $the_request ['gettype'];
		//dump('$gettype= ');
		switch ($gettype) {
			case "myoffer" :
				$uid = $the_request ['uid'];
				$data = M ( 'offer' )->getMyOffer($uid);
				break;
			case "nearby" :
				$map ['uid'] = $the_request ['uid'];
				$data = M ( 'offer' )->getNearOffer($uid);
				break;
			case "deadline" :
				$map ['uid'] = $the_request ['uid'];
				$data = M ( 'offer' )->getDeadlineOffer($uid);
				break;
			case "ctime" :
				break;
		
		}
		
		return null;
	}
	//得到登录数据
	public function regUser($the_request) {
		$mobile = $the_request ['mobile'];
		$user = $the_request ['user'];
		$pass = $the_request ['pass'];
		//dump("logid=".$email."pass=".$pass);
		if ((! $mobile) || (! $pass)) {
			echo "regname or password is empty!";		
		//return NULL;
		}
		$data ['uname'] = $user;
		$data ['mobile'] = $mobile;
		$data ['password'] = md5($pass);
		$userid = M('User')->add($data);		
		return intval ( $userid );		
	}
	//注册用户
	public function loginCheck($the_request) {
		
		$mobile = $the_request ['user'];
		$pass = $the_request ['pass'];
		//dump("logid=".$email."pass=".$pass);
		if ((! $mobile) || (! $pass)) {
			echo "Login name or password is empty!";
		
		//return NULL;
		}
		$map ['mobile'] = $mobile;
		$user = M ( 'User' )->where ( $map )->find ();
		if (md5 ( $pass ) != $user ['password']) {
			//dump ( $user );
			echo "Password is wrong \n " . $email . $pass . ' \n';
			echo $user ['password'] . "\n";
			dump ( md5 ( $pass ) );
			return NULL;
		} else {
			//echo "Login in sucessful";
			return  $user;
		}
	}	
	public function jasonout() {
		$json = $_SERVER ['HTTP_JSON'];
		echo "JSON: \n";
		echo "--------------\n";
		var_dump ( $json );
		echo "\n\n";
		
		$app = $_REQUEST ['app'];
		$jsonpost = $_REQUEST ['jsonpost'];
		echo ('======' . $app . "\n");
		var_dump ( $app );
		echo ("======jsonpost \n ==");
		dump ( $jsonpost );
		echo ("====== \n ");
		//$data = json_decode($json);
		$data = json_decode ( $data );
		echo "Array: \n";
		echo "--------------\n";
		var_dump ( $data );
		echo "\n\n";
		
		//$name = $data->name;
		//$pos = $data->position;
		echo "Result: \n";
		echo "--------------\n";
		echo "Name     : " . $name . "\n Position : " . $pos;
	}
	//获取所有需求
	public function getWeibo($the_request) {
		//dump('getWeibo  \n');
		$list = M ( 'Offer' )->findall ();
		return $list;
	}

	public function getList($the_request) {
		
		//$uid = $the_request ['uid'];
		$order = $the_request ['order'];
		$limit = $the_request ['limit'];
		if(!$limit) $limit = 20;
		//dump("uid=".$uid.' $order='.$order.'$limit'.$limit);
		
		//$map ['uid'] = $uid;
		
		switch ($order){
			case "nearby":
				//dump('case nearby uid='.$uid.' $order='.$order.'$limit'.$limit);
				$list = M ( 'Offer' )->order ( 'ctime desc' )->limit ( $limit )->findall ();
				break;
			case "time":
				//dump('case time uid='.$uid.' $order='.$order.'$limit'.$limit);
				$list = M ( 'Offer' )->order ( 'ctime desc' )->limit ( $limit )->findall ();
				break;
			case "price":
				//dump('case price uid='.$uid.' $order='.$order.'$limit'.$limit);
				$list = M ( 'Offer' )->order ( 'money desc' )->limit ( $limit )->findall ();
				break;	
			default:
				$list = M ( 'Weibo' )->limit ( 20 )->findall ();
				break;							
		}
		//$limit = 10;
		//$user = M('Weibo')->where($map)->findPage($limit);
		
		return $list;
	}
	//获取所有我的需求
	public function getMyNeedList($the_request) {
		
		$uid = $the_request ['uid'];
		//
		if ((! $uid)) {
			echo "Login name  empty!";
		}
		//dump('getMyNeedList uid = '.$uid);
		$map ['uid'] = $uid;
		$limit = 10;
		//$user = M('Weibo')->where($map)->findPage($limit);		
		$data = M ( 'Offer' )->where ( $map )->order ( 'ctime desc' )->limit ( 20 )->findall ();
		//dump($data);
		return $data;
	}
	//获取所有微博，做测试
	public function getMyOrderList($the_request) {
		
		$uid = $the_request ['uid'];
		//
		if ((! $uid)) {
			echo "Login name  empty!";
		}
		//dump('getMyOrderList uid = '.$uid);
		$map ['auid'] = $uid;
		$limit = 10;
		//$user = M('Weibo')->where($map)->findPage($limit);
		$data = M ( 'ViewAuction' )->where ( $map )->order ( 'actime desc' )->limit ( 20 )->findall ();
		
		return $data;
	}		
}
?>