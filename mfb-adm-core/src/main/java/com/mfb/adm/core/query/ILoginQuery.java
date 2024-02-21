package com.mfb.adm.core.query;

import com.mfb.adm.comm.dtos.UsuarioLogin;

public interface ILoginQuery {

	public UsuarioLogin login(String username);

}
