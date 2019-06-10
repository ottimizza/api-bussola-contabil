package br.com.ottimizza.dashboard.models;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Lucas Martins
 */
@NoArgsConstructor @AllArgsConstructor
public class Credentials implements Serializable {
    
    @Getter @Setter
    private String email;
    
    @Getter @Setter
    private String password;

}
